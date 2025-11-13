package com.example.order.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private OffsetDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public OrderEntity() {}

    public OrderEntity(String customerName) {
        this.customerName = customerName;
        this.createdAt = OffsetDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OrderStatus getStatus() { return status; }
    public List<OrderItem> getItems() { return items; }

    public void setId(Long id) { this.id = id; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public java.math.BigDecimal totalAmount() {
        return items.stream()
                .map(OrderItem::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addItem(OrderItem item) {
        item.setOrder(this);
        items.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderEntity)) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
