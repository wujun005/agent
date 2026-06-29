package com.example.demo.product1;

import java.util.List;

/**
 * 商品分页查询结果。
 */
public record Product1Page(
        List<Product1> records,
        long total,
        int pageNum,
        int pageSize
) {
}
