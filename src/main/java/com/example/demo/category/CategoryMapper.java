package com.example.demo.category;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CategoryMapper {

    List<Category> findAll();

    List<Category> findByNameContaining(@Param("name") String name);

    Category findById(@Param("id") Integer id);

    int insert(Category category);

    int update(Category category);

    int deleteById(@Param("id") Integer id);
}
