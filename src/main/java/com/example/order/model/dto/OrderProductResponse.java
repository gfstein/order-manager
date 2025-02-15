package com.example.order.model.dto;

import java.util.UUID;

public record OrderProductResponse(
        UUID productId,
        String name,

        Integer quantity
) {
}
