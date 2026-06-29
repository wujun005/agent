package com.example.demo.subCategory;

import java.util.List;

public record SubcategoryListResponse(
        int code,
        String msg,
        List<SubCategory> records,
        long total,
        int pageNum,
        int pageSize,
        long pages
) {
    public static SubcategoryListResponse success(SubcategotyPage page) {
        long pages = page.total() == 0 ? 0 : (page.total() + page.pageSize() - 1) / page.pageSize();
        return new SubcategoryListResponse(
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
