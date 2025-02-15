package com.example.order.controller;

import com.example.order.model.Order;
import com.example.order.model.dto.OrderResponse;
import com.example.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/pending-orders")
    public List<OrderResponse> getPendingOrders() {
        return orderService.getOrdersPending().stream().map(OrderResponse::fromModel).toList();
    }

}
