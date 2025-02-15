package com.example.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "PRODUCTS")
@Data
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    @Version // Adiciona controle de versão para otimistic locking
    @JsonIgnore
    private int version;

    public Product() {
    }
}
