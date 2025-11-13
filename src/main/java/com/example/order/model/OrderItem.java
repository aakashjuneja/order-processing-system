package com.example.order.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private int quantity;
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderEntity order;

    public OrderItem() {}

    public OrderItem(String productName, int quantity, java.math.BigDecimal price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() { return id; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public java.math.BigDecimal getPrice() { return price; }
    public OrderEntity getOrder() { return order; }

    public void setId(Long id) { this.id = id; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(java.math.BigDecimal price) { this.price = price; }
    public void setOrder(OrderEntity order) { this.order = order; }

    public java.math.BigDecimal total() {
        return price.multiply(java.math.BigDecimal.valueOf(quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
