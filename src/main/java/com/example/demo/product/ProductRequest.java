package com.example.demo.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 新增和修改商品时使用的请求体。
 */
public record ProductRequest(
        @NotBlank String productName,
        String description,
        @NotNull @DecimalMin("0.0") BigDecimal price,
        @NotNull @Min(0) Integer stock,
        @Min(0) @Max(1) Integer status
) {
}
