package com.example.order.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
public class OrderProduct implements Serializable {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;

    public OrderProduct() {
    }
}