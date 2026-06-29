package com.example.demo.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * product 表对应的领域对象。
 */
public record Product(
        Long id,
        String productName,
        String description,
        BigDecimal price,
        Integer stock,
        Integer status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
