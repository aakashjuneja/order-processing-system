package com.example.order.service;

import com.example.order.dto.CreateOrderRequest;
import com.example.order.model.OrderEntity;
import com.example.order.model.OrderStatus;
import com.example.order.repository.OrderRepository;
import com.example.order.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderRepository repo;
    private OrderServiceImpl service;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(OrderRepository.class);
        service = new OrderServiceImpl(repo);
    }

    @Test
    void createOrder_savesAndReturns() {
        var req = new CreateOrderRequest("Alice", List.of(new CreateOrderRequest.OrderItemDto("P1", 2, BigDecimal.valueOf(10))));
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));
        var o = service.createOrder(req);
        assertNotNull(o);
        assertEquals("Alice", o.getCustomerName());
        assertEquals(1, o.getItems().size());
        verify(repo, times(1)).save(any());
    }

    @Test
    void cancelOrder_onlyPending() {
        var e = new OrderEntity("Bob");
        e.setId(1L);
        e.setStatus(OrderStatus.PENDING);
        when(repo.findById(1L)).thenReturn(Optional.of(e));
        boolean res = service.cancelOrder(1L);
        assertTrue(res);
        assertEquals(OrderStatus.CANCELLED, e.getStatus());
        verify(repo).save(e);
    }

    @Test
    void listOrders_byStatus() {
        when(repo.findByStatus(OrderStatus.PENDING)).thenReturn(List.of(new OrderEntity("C")));
        var res = service.listOrders(Optional.of(OrderStatus.PENDING));
        assertEquals(1, res.size());
    }
}
