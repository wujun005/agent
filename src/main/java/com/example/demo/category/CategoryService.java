package com.example.demo.category;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public List<Category> findAll(String name) {
        if (!StringUtils.hasText(name)) {
            return categoryMapper.findAll();
        }
        return categoryMapper.findByNameContaining(name.trim());
    }

    public Integer save(Category category) {
        return categoryMapper.insert(category);
    }

    public  Integer update(Category category) {
        return  categoryMapper.update(category);
    }

    public  Integer delete(Integer id) {
        return categoryMapper.deleteById(id);
    }


}
