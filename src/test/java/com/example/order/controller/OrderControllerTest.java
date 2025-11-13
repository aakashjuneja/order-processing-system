package com.example.order.controller;

import com.example.order.dto.CreateOrderRequest;
import com.example.order.model.OrderEntity;
import com.example.order.model.OrderItem;
import com.example.order.model.OrderStatus;
import com.example.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createOrder_endpoint() throws Exception {
        var req = new CreateOrderRequest("Z", List.of(new CreateOrderRequest.OrderItemDto("P",1, BigDecimal.TEN)));
        var e = new OrderEntity("Z");
        e.addItem(new OrderItem("P",1, BigDecimal.TEN));
        when(service.createOrder(any())).thenReturn(e);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Z"));
    }

    @Test
    void cancel_conflictWhenNotPending() throws Exception {
        when(service.cancelOrder(1L)).thenReturn(false);
        mockMvc.perform(post("/api/orders/1/cancel")).andExpect(status().isConflict());
    }
}
