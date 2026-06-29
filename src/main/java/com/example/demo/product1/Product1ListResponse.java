package com.example.demo.product1;

import java.util.List;

/**
 * product1 商品列表接口的响应结构。
 */
public record Product1ListResponse(
        int code,
        String msg,
        List<Product1> records,
        long total,
        int pageNum,
        int pageSize,
        long pages
) {

    public static Product1ListResponse success(Product1Page page) {
        long pages = page.total() == 0 ? 0 : (page.total() + page.pageSize() - 1) / page.pageSize();
        return new Product1ListResponse(
                200,
                "success",
                page.records(),
                page.total(),
                page.pageNum(),
                page.pageSize(),
                pages
        );
    }
}
