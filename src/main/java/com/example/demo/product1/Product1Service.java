package com.example.demo.product1;

import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

/**
 * product1 商品 CRUD 业务层。
 */
@Service
public class Product1Service {

    private static final int DEFAULT_PAGE_NUM = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;

    private final Product1Mapper product1Mapper;

    public Product1Service(Product1Mapper product1Mapper) {
        this.product1Mapper = product1Mapper;
    }

    public List<Product1> findAll(String productName) {
        if (!StringUtils.hasText(productName)) {
            return product1Mapper.findAll();
        }

        return product1Mapper.findByProductNameContaining(productName.trim());
    }

    public Product1Page findPage(String productName, Integer pageNum, Integer pageSize) {
        int safePageNum = resolvePageNum(pageNum);
        int safePageSize = resolvePageSize(pageSize);
        String keyword = StringUtils.hasText(productName) ? productName.trim() : null;
        long total = product1Mapper.count(keyword);
        int offset = (safePageNum - 1) * safePageSize;
        List<Product1> records = total == 0
                ? Collections.emptyList()
                : product1Mapper.findPage(keyword, offset, safePageSize);

        return new Product1Page(records, total, safePageNum, safePageSize);
    }

    public Product1 getById(Long id) {
        Product1 product = product1Mapper.findById(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id);
        }
        return product;
    }

    public void create(Product1Request request) {
        Product1 product = new Product1();
        product.setProductName(request.productName());
        product.setDescription(request.description());
        product.setCategoryId(request.categoryId());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setStatus(resolveStatus(request.status()));

        product1Mapper.insert(product);
    }

    public Product1 update(Long id, Product1Request request) {
        Product1 product = new Product1();
        product.setId(id);
        product.setProductName(request.productName());
        product.setDescription(request.description());
        product.setCategoryId(request.categoryId());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setStatus(resolveStatus(request.status()));

        if (product1Mapper.update(product) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id);
        }

        return getById(id);
    }

    public void delete(Long id) {
        if (product1Mapper.deleteById(id) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id);
        }
    }

    private int resolveStatus(Integer status) {
        return status == null ? 1 : status;
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
