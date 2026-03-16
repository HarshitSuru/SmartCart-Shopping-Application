package com.shoppingcart.utils;

import com.shoppingcart.dto.CartDtos;
import com.shoppingcart.model.Cart;
import com.shoppingcart.model.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CartMapper implements GenericMapper<Cart, CartDtos.CartResponse> {

    @Override
    public CartDtos.CartResponse toDto(Cart cart) {
        List<CartDtos.CartItemResponse> items = cart.getItems().stream()
                .map(this::toItemDto)
                .toList();
        return new CartDtos.CartResponse(cart.getId(), items, cart.getTotalPrice());
    }

    private CartDtos.CartItemResponse toItemDto(CartItem item) {
        BigDecimal subtotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        return new CartDtos.CartItemResponse(
                item.getProductId(),
                item.getProductName(),
                item.getUnitPrice(),
                item.getQuantity(),
                subtotal,
                item.getImageUrl()
        );
    }
}
