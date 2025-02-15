package com.example.order.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "ORDERS")
@Data
@AllArgsConstructor
public class Order {

    @Id
    private UUID id;
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ORDER_PRODUCTS", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderProduct> products = new ArrayList<>();

    public Order() {
    }

    public Order(UUID id, Status status) {
        this.id = id;
        this.status = status;
    }

    public static enum Status {
        PENDING, SHIPPED, DELIVERED
    }


}