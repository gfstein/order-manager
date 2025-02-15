package com.example.order.model.dto;

import com.example.order.model.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record OrderRequest(
        @NotNull
        UUID orderId,

        @NotNull
        @Size(min = 1)
        List<Product> products
) {
}
