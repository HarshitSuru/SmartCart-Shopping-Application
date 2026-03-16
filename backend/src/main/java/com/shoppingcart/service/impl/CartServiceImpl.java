package com.shoppingcart.service.impl;

import com.shoppingcart.dto.CartDtos;
import com.shoppingcart.exception.BadRequestException;
import com.shoppingcart.exception.ResourceNotFoundException;
import com.shoppingcart.model.Cart;
import com.shoppingcart.model.CartItem;
import com.shoppingcart.model.Product;
import com.shoppingcart.repository.CartRepository;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.service.CartService;
import com.shoppingcart.utils.CartMapper;
import com.shoppingcart.utils.GuestSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository,
                           ProductRepository productRepository,
                           CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartDtos.CartResponse getCart() {
        Cart cart = getOrCreateCart();
        recalculateTotal(cart);
        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    public CartDtos.CartResponse addItem(CartDtos.AddCartItemRequest request) {
        Product product = getProduct(request.productId());
        validateInventory(product, request.quantity());
        Cart cart = getOrCreateCart();

        Optional<CartItem> existing = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.productId()))
                .findFirst();

        if (existing.isPresent()) {
            CartItem item = existing.get();
            int newQuantity = item.getQuantity() + request.quantity();
            validateInventory(product, newQuantity);
            item.setQuantity(newQuantity);
        } else {
            CartItem item = new CartItem();
            item.setProductId(product.getId());
            item.setProductName(product.getName());
            item.setUnitPrice(product.getPrice());
            item.setQuantity(request.quantity());
            item.setImageUrl(product.getImageUrl());
            cart.getItems().add(item);
        }

        recalculateTotal(cart);
        LOGGER.info("Added product {} to guest cart", request.productId());
        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    public CartDtos.CartResponse updateItem(CartDtos.UpdateCartItemRequest request) {
        Product product = getProduct(request.productId());
        validateInventory(product, request.quantity());
        Cart cart = getOrCreateCart();
        CartItem item = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProductId().equals(request.productId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        item.setQuantity(request.quantity());
        recalculateTotal(cart);
        LOGGER.info("Updated cart item {} for guest cart", request.productId());
        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    public CartDtos.CartResponse removeItem(String productId) {
        Cart cart = getOrCreateCart();
        boolean removed = cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        if (!removed) {
            throw new ResourceNotFoundException("Cart item not found");
        }
        recalculateTotal(cart);
        LOGGER.info("Removed cart item {} from guest cart", productId);
        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    public void clearCart() {
        Cart cart = cartRepository.findByUserId(GuestSession.USER_ID)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    private Product getProduct(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private Cart getOrCreateCart() {
        return cartRepository.findByUserId(GuestSession.USER_ID).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUserId(GuestSession.USER_ID);
            return cartRepository.save(cart);
        });
    }

    private void validateInventory(Product product, int quantity) {
        if (product.getInventory() < quantity) {
            throw new BadRequestException("Requested quantity exceeds inventory");
        }
    }

    private void recalculateTotal(Cart cart) {
        BigDecimal total = cart.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(total);
    }
}
