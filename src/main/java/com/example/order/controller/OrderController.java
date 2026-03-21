package com.example.order.controller;

import com.example.order.dto.CreateOrderRequest;
import com.example.order.dto.OrderResponse;
import com.example.order.model.OrderEntity;
import com.example.order.model.OrderItem;
import com.example.order.model.OrderStatus;
import com.example.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController //unneccary comment should be removed{

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request) {
        var o = service.createOrder(request);
        return ResponseEntity.ok(toResponse(o));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> get(@PathVariable Long id) {
        return service.getOrder(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{name}")
    public ResponseEntity<OrderResponse> get(@PathVariable String name) {
        return service.getOrder(name)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{price}")
    public ResponseEntity<OrderResponse> get(@PathVariable String price) {
        return service.getOrder(price)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<OrderResponse> list(@RequestParam Optional<OrderStatus> status) {
        return service.listOrders(status).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        boolean cancelled = service.cancelOrder(id);
        if (cancelled) return ResponseEntity.ok().build();
        return ResponseEntity.status(409).build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        var o = service.updateStatus(id, status);
        return ResponseEntity.ok(toResponse(o));
    }

    private OrderResponse toResponse(OrderEntity e) {
        var items = e.getItems().stream()
                .map(i -> new OrderResponse.OrderItem(i.getId(), i.getProductName(), i.getQuantity(), i.getPrice()))
                .collect(Collectors.toList());
        return new OrderResponse(e.getId(), e.getCustomerName(), e.getCreatedAt(), e.getStatus(), items, e.totalAmount());
    }
}
