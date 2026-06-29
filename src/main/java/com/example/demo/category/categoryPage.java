package com.example.demo.category;

import java.util.List;

public record CategoryPage(
        long total,
        int pageSize,
        int pageNum,
        List<Category> records
) {
}
