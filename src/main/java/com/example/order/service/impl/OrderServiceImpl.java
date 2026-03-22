package com.example.order.service.impl;

import com.example.order.dto.CreateOrderRequest;
import com.example.order.model.OrderEntity;
import com.example.order.model.OrderItem;
import com.example.order.model.OrderStatus;
import com.example.order.repository.OrderRepository;
import com.example.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderEntity createOrder(CreateOrderRequest request) {
        var order = new OrderEntity(request.customerName());
        request.items().forEach(i -> {
            var item = new OrderItem(i.productName(), i.quantity(), i.price());
            order.addItem(item);
        });
        return repository.save(order);
    }

    @Override
    public Optional<OrderEntity> getOrder(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<OrderEntity> listOrders(Optional<OrderStatus> status) {
        return status.map(repository::findByStatus).orElseGet(repository::findAll);
    }

    @Override
    public boolean cancelOrder(Long id) {
        return repository.findById(id).filter(o -> o.getStatus() == OrderStatus.PENDING)
                .map(o -> {
                    o.setStatus(OrderStatus.CANCELLED);
                    repository.save(o);
                    return true;
                }).orElse(false);
    }

    @Override
    public void promotePendingToProcessing() {
        var pending = repository.findByStatus(OrderStatus.PENDING);
        pending.stream().forEach(o -> {
            o.setStatus(OrderStatus.PROCESSING);
            repository.save(o);
        });
    }
@Override
    public void promotePendingToProcessing2() {
        var pending = repository.findByStatus(OrderStatus.PENDING);
        pending.stream().forEach(o -> {
            o.setStatus(OrderStatus.PROCESSING);
            repository.save(o);
        });
        //commentttttttt
    }
    @Override
    public OrderEntity updateStatus(Long id, OrderStatus status) {
        var o = repository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        o.setStatus(status);
        return repository.save(o);
    }
}
