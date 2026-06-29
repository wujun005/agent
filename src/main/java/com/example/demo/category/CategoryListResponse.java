package com.example.demo.category;

import java.util.List;

public record CategoryListResponse(
        int code,
        String msg,
        List<Category> records,
        long total,
        int pageNum,
        int pageSize,
        long pages
) {
    public static CategoryListResponse success(CategoryPage page) {
        long pages = page.total() == 0 ? 0 : (page.total() + page.pageSize() - 1) / page.pageSize();
        return new CategoryListResponse(
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
