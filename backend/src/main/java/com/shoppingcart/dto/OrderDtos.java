package com.shoppingcart.dto;

import com.shoppingcart.model.OrderStatus;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public final class OrderDtos {

    private OrderDtos() {
    }

    public record CheckoutRequest(
            @NotBlank String shippingAddress,
            @NotBlank String paymentMethod
    ) {
    }

    public record OrderItemResponse(
            String productId,
            String productName,
            BigDecimal unitPrice,
            int quantity,
            BigDecimal subtotal
    ) {
    }

    public record OrderResponse(
            String id,
            List<OrderItemResponse> items,
            BigDecimal totalPrice,
            OrderStatus status,
            String shippingAddress,
            String paymentMethod,
            Instant createdAt
    ) {
    }
}
