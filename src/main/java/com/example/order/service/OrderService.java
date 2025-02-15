package com.example.order.service;

import com.example.order.model.Order;
import com.example.order.model.OrderProduct;
import com.example.order.model.dto.OrderRequest;
import com.example.order.repository.OrderRepository;
import com.example.order.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Log
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, ObjectMapper objectMapper,
                        KafkaTemplate<String, String> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    @KafkaListener(topics = "order", groupId = "order-group")
    public void createOrder(String message) throws JsonProcessingException {

        try {
            OrderRequest orderRequest = objectMapper.readValue(message, OrderRequest.class);

            orderRepository.findById(orderRequest.orderId()).ifPresent(order -> {
                throw new IllegalArgumentException("Order already exists");
            });

            Order order = new Order(orderRequest.orderId(), Order.Status.PENDING);
            AtomicReference<BigDecimal> total = new AtomicReference<>(new BigDecimal(0));
            orderRequest.products().forEach(requestProduct -> {
                productRepository.findById(requestProduct.getId()).ifPresentOrElse(
                    stockProduct -> {
                        if (stockProduct.getQuantity() < requestProduct.getQuantity()) {
                            throw new IllegalArgumentException("Not enough products in stock");
                        }

                        stockProduct.setQuantity(stockProduct.getQuantity() - requestProduct.getQuantity());
                        total.getAndAccumulate(stockProduct.getPrice().multiply(new BigDecimal(requestProduct.getQuantity())), BigDecimal::add);

                        OrderProduct orderProduct = new OrderProduct(stockProduct, requestProduct.getQuantity());
                        order.getProducts().add(orderProduct);
                    },
                    () -> {
                        throw new IllegalArgumentException("Product not found");
                    }
                );
            });

            order.setTotal(total.get());
            Order savedOrder = orderRepository.save(order);
            kafkaTemplate.send("order-calculated", savedOrder.getId().toString());
        } catch (Exception e) {
            log.severe("Error processing order: " + e.getMessage());
            throw e;
        }
    }

    public List<Order> getOrdersPending() {
        return orderRepository.findByStatus(Order.Status.PENDING);
    }

}
