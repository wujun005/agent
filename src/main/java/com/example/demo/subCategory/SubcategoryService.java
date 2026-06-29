package com.example.demo.subCategory;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class SubcategoryService {

    private static final int DEFAULT_PAGE_NUM = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;

    private final SubCategoryMapper subCategoryMapper;

    public SubcategoryService(SubCategoryMapper subCategoryMapper) {
        this.subCategoryMapper = subCategoryMapper;
    }

    public List<SubCategory> findAll() {
        return subCategoryMapper.findAll();
    }

    public SubcategotyPage findPage(String name, Integer pageNum, Integer pageSize) {
        int safePageNum = resolvePageNum(pageNum);
        int safePageSize = resolvePageSize(pageSize);
        String keyword = StringUtils.hasText(name) ? name.trim() : null;
        long total = subCategoryMapper.count(keyword);
        int offset = (safePageNum - 1) * safePageSize;
        List<SubCategory> records = total == 0
                ? Collections.emptyList()
                : subCategoryMapper.findPage(keyword, offset, safePageSize);

        return new SubcategotyPage(records, total, safePageNum, safePageSize);
    }

    public int save(SubCategory subCategory) {
        return subCategoryMapper.insert(subCategory);
    }

    public int update(SubCategory subCategory) {
        return subCategoryMapper.update(subCategory);
    }

    public int delete(Long id) {
        return subCategoryMapper.delete(id);
    }

    private int resolvePageNum(Integer pageNum) {
        return pageNum == null || pageNum < 1 ? DEFAULT_PAGE_NUM : pageNum;
    }

    private int resolvePageSize(Integer pageSize) {
        if (pageSize == null || pageSize < 1) {
            return DEFAULT_PAGE_SIZE;
        }
        return Math.min(pageSize, MAX_PAGE_SIZE);
    }
}
