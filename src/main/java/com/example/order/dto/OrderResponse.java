package com.example.order.dto;

import com.example.order.model.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record OrderResponse(Long id, String customerName, OffsetDateTime createdAt, OrderStatus status, List<OrderItem> items, BigDecimal total) {
    public record OrderItem(Long id, String productName, int quantity, BigDecimal price) {}
}
