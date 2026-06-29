package com.example.demo.product1;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * product1 商品增删改查接口。
 */
@RestController
@RequestMapping("/api/product1/products")
public class Product1Controller {

    private final Product1Service product1Service;

    public Product1Controller(Product1Service product1Service) {
        this.product1Service = product1Service;
    }

    @GetMapping
    public Product1ListResponse listProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return Product1ListResponse.success(product1Service.findPage(productName, pageNum, pageSize));
    }

    @GetMapping("/{id}")
    public Product1 getProduct(@PathVariable Long id) {
        return product1Service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product1StatusResponse createProduct(@Valid @RequestBody Product1Request request) {
        product1Service.create(request);
        return Product1StatusResponse.success();
    }

    @PutMapping("/{id}")
    public Product1 updateProduct(@PathVariable Long id, @Valid @RequestBody Product1Request request) {
        return product1Service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        product1Service.delete(id);
    }
}
