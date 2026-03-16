package com.shoppingcart.controller;

import com.shoppingcart.dto.ApiResponse;
import com.shoppingcart.dto.OrderDtos;
import com.shoppingcart.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${app.cors.allowed-origin}")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<OrderDtos.OrderResponse>> checkout(@Valid @RequestBody OrderDtos.CheckoutRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("Checkout completed successfully", orderService.checkout(request)));
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderDtos.OrderResponse>>> getOrders() {
        return ResponseEntity.ok(new ApiResponse<>("Orders fetched successfully", orderService.getOrders()));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderDtos.OrderResponse>> getOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(new ApiResponse<>("Order fetched successfully", orderService.getOrderById(orderId)));
    }
}
