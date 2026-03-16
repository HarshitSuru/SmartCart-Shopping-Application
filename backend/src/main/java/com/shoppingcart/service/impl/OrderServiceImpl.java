package com.shoppingcart.service.impl;

import com.shoppingcart.dto.OrderDtos;
import com.shoppingcart.exception.BadRequestException;
import com.shoppingcart.exception.ResourceNotFoundException;
import com.shoppingcart.model.Cart;
import com.shoppingcart.model.CartItem;
import com.shoppingcart.model.Order;
import com.shoppingcart.model.OrderItem;
import com.shoppingcart.model.OrderStatus;
import com.shoppingcart.model.Product;
import com.shoppingcart.repository.CartRepository;
import com.shoppingcart.repository.OrderRepository;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.service.OrderService;
import com.shoppingcart.utils.GuestSession;
import com.shoppingcart.utils.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CartRepository cartRepository,
                            ProductRepository productRepository,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDtos.OrderResponse checkout(OrderDtos.CheckoutRequest request) {
        Cart cart = cartRepository.findByUserId(GuestSession.USER_ID)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        Order order = new Order();
        order.setUserId(GuestSession.USER_ID);
        order.setUserEmail(GuestSession.EMAIL);
        order.setShippingAddress(request.shippingAddress());
        order.setPaymentMethod(request.paymentMethod());
        order.setStatus(OrderStatus.PENDING);
        order.setItems(cart.getItems().stream().map(this::toOrderItem).toList());
        order.setTotalPrice(cart.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        Order savedOrder = orderRepository.save(order);
        decrementInventory(cart.getItems());
        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
        processOrderAsync(savedOrder.getId());

        LOGGER.info("Checkout completed for guest cart with order {}", savedOrder.getId());
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public List<OrderDtos.OrderResponse> getOrders() {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(GuestSession.USER_ID).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDtos.OrderResponse getOrderById(String orderId) {
        Order order = orderRepository.findById(orderId)
                .filter(existing -> existing.getUserId().equals(GuestSession.USER_ID))
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return orderMapper.toDto(order);
    }

    private OrderItem toOrderItem(CartItem item) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(item.getProductId());
        orderItem.setProductName(item.getProductName());
        orderItem.setUnitPrice(item.getUnitPrice());
        orderItem.setQuantity(item.getQuantity());
        orderItem.setSubtotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        return orderItem;
    }

    private void decrementInventory(List<CartItem> cartItems) {
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found during checkout"));
            if (product.getInventory() < item.getQuantity()) {
                throw new BadRequestException("Insufficient inventory for " + product.getName());
            }
            product.setInventory(product.getInventory() - item.getQuantity());
            productRepository.save(product);
        }
    }

    @Async("orderTaskExecutor")
    public CompletableFuture<Void> processOrderAsync(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(OrderStatus.PROCESSING);
        orderRepository.save(order);
        try {
            Thread.sleep(1500L);
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);
            LOGGER.info("Order {} processed successfully", orderId);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            order.setStatus(OrderStatus.FAILED);
            orderRepository.save(order);
            LOGGER.error("Order processing interrupted for {}", orderId, exception);
        }
        return CompletableFuture.completedFuture(null);
    }
}
