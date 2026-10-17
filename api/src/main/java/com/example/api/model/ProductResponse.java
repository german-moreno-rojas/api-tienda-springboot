package com.example.api.model;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, BigDecimal price) {

    public static ProductResponse from(Product p){
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getPrice()
        );
    }
}
