package com.example.demo.product;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

/**
 * 商品 CRUD 业务层。
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll(String productName) {
        if (!StringUtils.hasText(productName)) {
            return productRepository.findAll();
        }

        return productRepository.findByProductNameContaining(productName.trim());
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id));
    }

    public Product create(ProductRequest request) {
        Product product = new Product(
                null,
                request.productName(),
                request.description(),
                request.price(),
                request.stock(),
                resolveStatus(request.status()),
                null,
                null
        );

        return productRepository.save(product);
    }

    public Product update(Long id, ProductRequest request) {
        Product product = new Product(
                id,
                request.productName(),
                request.description(),
                request.price(),
                request.stock(),
                resolveStatus(request.status()),
                null,
                null
        );

        if (!productRepository.update(product)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id);
        }

        return getById(id);
    }

    public void delete(Long id) {
        if (!productRepository.deleteById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id);
        }
    }

    private int resolveStatus(Integer status) {
        return status == null ? 1 : status;
    }
}
