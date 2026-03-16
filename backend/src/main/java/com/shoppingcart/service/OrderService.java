package com.shoppingcart.service;

import com.shoppingcart.dto.OrderDtos;

import java.util.List;

public interface OrderService {

    OrderDtos.OrderResponse checkout(OrderDtos.CheckoutRequest request);

    List<OrderDtos.OrderResponse> getOrders();

    OrderDtos.OrderResponse getOrderById(String orderId);
}
