package com.shoppingcart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

public final class CartDtos {

    private CartDtos() {
    }

    public record AddCartItemRequest(
            @NotBlank String productId,
            @Min(1) int quantity
    ) {
    }

    public record UpdateCartItemRequest(
            @NotBlank String productId,
            @Min(1) int quantity
    ) {
    }

    public record RemoveCartItemRequest(
            @NotBlank String productId
    ) {
    }

    public record CartItemResponse(
            String productId,
            String productName,
            BigDecimal unitPrice,
            int quantity,
            BigDecimal subtotal,
            String imageUrl
    ) {
    }

    public record CartResponse(
            String id,
            List<CartItemResponse> items,
            BigDecimal totalPrice
    ) {
    }
}
