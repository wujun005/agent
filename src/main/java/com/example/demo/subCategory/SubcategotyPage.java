package com.example.demo.subCategory;

import java.util.List;

public record SubcategotyPage(
        List<SubCategory> records,
        long total,
        int pageNum,
        int pageSize
) {
}
