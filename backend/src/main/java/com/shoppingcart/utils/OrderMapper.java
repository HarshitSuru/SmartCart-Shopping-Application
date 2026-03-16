package com.shoppingcart.utils;

import com.shoppingcart.dto.OrderDtos;
import com.shoppingcart.model.Order;
import com.shoppingcart.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper implements GenericMapper<Order, OrderDtos.OrderResponse> {

    @Override
    public OrderDtos.OrderResponse toDto(Order order) {
        List<OrderDtos.OrderItemResponse> items = order.getItems().stream()
                .map(this::toItemDto)
                .toList();
        return new OrderDtos.OrderResponse(
                order.getId(),
                items,
                order.getTotalPrice(),
                order.getStatus(),
                order.getShippingAddress(),
                order.getPaymentMethod(),
                order.getCreatedAt()
        );
    }

    private OrderDtos.OrderItemResponse toItemDto(OrderItem item) {
        return new OrderDtos.OrderItemResponse(
                item.getProductId(),
                item.getProductName(),
                item.getUnitPrice(),
                item.getQuantity(),
                item.getSubtotal()
        );
    }
}
