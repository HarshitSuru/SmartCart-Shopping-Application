package com.shoppingcart.service;

import com.shoppingcart.dto.CartDtos;
import com.shoppingcart.model.Cart;
import com.shoppingcart.model.CartItem;
import com.shoppingcart.utils.CartMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class CartMapperTest {

    private final CartMapper cartMapper = new CartMapper();

    @Test
    void shouldMapCartSubtotalAndTotal() {
        CartItem item = new CartItem();
        item.setProductId("p1");
        item.setProductName("Headphones");
        item.setUnitPrice(new BigDecimal("100.00"));
        item.setQuantity(2);

        Cart cart = new Cart();
        cart.setId("c1");
        cart.setItems(List.of(item));
        cart.setTotalPrice(new BigDecimal("200.00"));

        CartDtos.CartResponse response = cartMapper.toDto(cart);

        Assertions.assertEquals(new BigDecimal("200.00"), response.totalPrice());
        Assertions.assertEquals(new BigDecimal("200.00"), response.items().get(0).subtotal());
    }
}
