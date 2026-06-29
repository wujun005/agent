package com.example.demo.product1;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 使用 MyBatis 操作 product 表。
 */
@Mapper
public interface Product1Mapper {

    List<Product1> findAll();

    List<Product1> findByProductNameContaining(@Param("productName") String productName);

    List<Product1> findPage(
            @Param("productName") String productName,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize
    );

    long count(@Param("productName") String productName);

    Product1 findById(@Param("id") Long id);

    int insert(Product1 product);

    int update(Product1 product);

    int deleteById(@Param("id") Long id);
}
