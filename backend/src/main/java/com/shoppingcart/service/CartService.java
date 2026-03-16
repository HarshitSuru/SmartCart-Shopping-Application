package com.shoppingcart.service;

import com.shoppingcart.dto.CartDtos;

public interface CartService {

    CartDtos.CartResponse getCart();

    CartDtos.CartResponse addItem(CartDtos.AddCartItemRequest request);

    CartDtos.CartResponse updateItem(CartDtos.UpdateCartItemRequest request);

    CartDtos.CartResponse removeItem(String productId);

    void clearCart();
}
