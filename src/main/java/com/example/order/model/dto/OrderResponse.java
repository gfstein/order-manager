package com.example.order.model.dto;

import com.example.order.model.Order;
import com.example.order.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        BigDecimal total,
        String status,
        List<Product> products
) {

    public static OrderResponse fromModel(Order order) {
        return new OrderResponse(order.getId(), order.getTotal(), order.getStatus().name(), order.getProducts().stream().map(
                orderProduct -> new Product(
                        orderProduct.getProduct().getId(),
                        orderProduct.getProduct().getName(),
                        orderProduct.getProduct().getPrice(),
                        orderProduct.getProduct().getVersion(),
                        orderProduct.getQuantity()
                )
        ).toList());
    }

}
