package com.shoppingcart.controller;

import com.shoppingcart.dto.ApiResponse;
import com.shoppingcart.dto.CartDtos;
import com.shoppingcart.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "${app.cors.allowed-origin}")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CartDtos.CartResponse>> getCart() {
        return ResponseEntity.ok(new ApiResponse<>("Cart fetched successfully", cartService.getCart()));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CartDtos.CartResponse>> addToCart(@Valid @RequestBody CartDtos.AddCartItemRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("Item added to cart", cartService.addItem(request)));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<CartDtos.CartResponse>> updateCart(@Valid @RequestBody CartDtos.UpdateCartItemRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("Cart updated successfully", cartService.updateItem(request)));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<CartDtos.CartResponse>> removeFromCart(@RequestParam String productId) {
        return ResponseEntity.ok(new ApiResponse<>("Item removed from cart", cartService.removeItem(productId)));
    }
}
