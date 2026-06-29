package com.example.demo.subCategory;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubCategoryMapper {
    List<SubCategory> findAll();

    List<SubCategory> findPage(
            @Param("name") String name,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize
    );

    long count(@Param("name") String name);

    int insert(SubCategory subCategory);

    int update(SubCategory subCategory);

    int delete(@Param("id") Long id);
}
