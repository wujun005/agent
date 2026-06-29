package com.example.demo.product1;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * product1 的 HTTP 接口测试。
 */
@WebMvcTest(Product1Controller.class)
class Product1ApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Product1Service product1Service;

    @Test
    void shouldListProducts() throws Exception {
        when(product1Service.findPage(null, 1, 10))
                .thenReturn(new Product1Page(List.of(sampleProduct(1L)), 1L, 1, 10));

        mockMvc.perform(get("/api/product1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("success"))
                .andExpect(jsonPath("$.records[0].id").value(1))
                .andExpect(jsonPath("$.records[0].productName").value("机械键盘 K87"))
                .andExpect(jsonPath("$.records[0].categoryName").value("办公外设"))
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.pageNum").value(1))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.pages").value(1));

        verify(product1Service).findPage(null, 1, 10);
    }

    @Test
    void shouldSearchProductsByProductName() throws Exception {
        when(product1Service.findPage("键盘", 2, 5))
                .thenReturn(new Product1Page(List.of(sampleProduct(1L)), 9L, 2, 5));

        mockMvc.perform(get("/api/product1/products")
                        .param("productName", "键盘")
                        .param("pageNum", "2")
                        .param("pageSize", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.records[0].productName").value("机械键盘 K87"))
                .andExpect(jsonPath("$.total").value(9))
                .andExpect(jsonPath("$.pageNum").value(2))
                .andExpect(jsonPath("$.pageSize").value(5))
                .andExpect(jsonPath("$.pages").value(2));

        verify(product1Service).findPage("键盘", 2, 5);
    }

    @Test
    void shouldGetProductById() throws Exception {
        when(product1Service.getById(1L)).thenReturn(sampleProduct(1L));

        mockMvc.perform(get("/api/product1/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productName").value("机械键盘 K87"))
                .andExpect(jsonPath("$.price").value(329.00));

        verify(product1Service).getById(1L);
    }

    @Test
    void shouldCreateProduct() throws Exception {
        mockMvc.perform(post("/api/product1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "productName": "机械键盘 K87",
                                  "description": "87 键机械键盘",
                                  "categoryId": 4,
                                  "price": 329.00,
                                  "stock": 55,
                                  "status": 1
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("success"));

        verify(product1Service).create(any(Product1Request.class));
    }

    @Test
    void shouldRejectInvalidCreateRequest() throws Exception {
        mockMvc.perform(post("/api/product1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "productName": "",
                                  "description": "87 键机械键盘",
                                  "categoryId": null,
                                  "price": -1,
                                  "stock": -1,
                                  "status": 2
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        Product1 updated = new Product1(
                1L,
                "AirPods Pro 2",
                "主动降噪无线耳机",
                1L,
                "手机数码",
                new BigDecimal("1899.00"),
                30,
                1,
                LocalDateTime.of(2026, 5, 29, 12, 0),
                LocalDateTime.of(2026, 5, 29, 12, 30)
        );

        when(product1Service.update(any(Long.class), any(Product1Request.class))).thenReturn(updated);

        mockMvc.perform(put("/api/product1/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "productName": "AirPods Pro 2",
                                  "description": "主动降噪无线耳机",
                                  "categoryId": 1,
                                  "price": 1899.00,
                                  "stock": 30,
                                  "status": 1
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productName").value("AirPods Pro 2"));

        verify(product1Service).update(any(Long.class), any(Product1Request.class));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/product1/products/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(product1Service).delete(1L);
    }

    private Product1 sampleProduct(Long id) {
        return new Product1(
                id,
                "机械键盘 K87",
                "87 键机械键盘",
                4L,
                "办公外设",
                new BigDecimal("329.00"),
                55,
                1,
                LocalDateTime.of(2026, 5, 29, 12, 0),
                LocalDateTime.of(2026, 5, 29, 12, 30)
        );
    }
}
