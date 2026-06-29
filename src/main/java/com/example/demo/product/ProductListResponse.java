package com.example.demo.product;

import java.util.List;

/**
 * 商品列表接口的响应结构。
 */
public record ProductListResponse(
        int code,
        String msg,
        List<Product> records,
        long total
) {

    public static ProductListResponse success(List<Product> records) {
        return new ProductListResponse(200, "success", records, records.size());
    }
}
