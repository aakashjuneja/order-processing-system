package com.example.order.service;

import com.example.order.dto.CreateOrderRequest;
import com.example.order.model.OrderEntity;
import com.example.order.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderEntity createOrder(CreateOrderRequest request);
    Optional<OrderEntity> getOrder(Long id);
    List<OrderEntity> listOrders(Optional<OrderStatus> status);
    boolean cancelOrder(Long id);
    void promotePendingToProcessing();
    OrderEntity updateStatus(Long id, OrderStatus status);
}
