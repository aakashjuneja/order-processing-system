package com.example.order.dto;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest(String customerName, List<OrderItemDto> items) {
    public record OrderItemDto(String productName, int quantity, BigDecimal price) {}
}
