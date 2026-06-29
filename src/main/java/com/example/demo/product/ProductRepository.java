package com.example.demo.product;

import java.util.List;
import java.util.Optional;

/**
 * 商品数据访问接口。
 */
public interface ProductRepository {

    List<Product> findAll();

    List<Product> findByProductNameContaining(String productName);

    Optional<Product> findById(Long id);

    Product save(Product product);

    boolean update(Product product);

    boolean deleteById(Long id);
}
