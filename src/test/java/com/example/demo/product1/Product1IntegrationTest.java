package com.example.demo.product1;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

/**
 * product1 的接口集成测试，会真实访问 MySQL 和 MyBatis Mapper。
 */
@SpringBootTest(properties = "openai.api-key=test-key")
@AutoConfigureMockMvc
@Sql(scripts = "/sql/product1-integration-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/product1-integration-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class Product1IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Product1Mapper product1Mapper;

    @Test
    void shouldSearchProductsFromDatabaseThroughHttp() throws Exception {
        String responseBody = mockMvc.perform(get("/api/product1/products").param("productName", "IT-P1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
        System.out.println("接口返回 Body：");
        System.out.println(responseBody);
    }

    @Test
    void shouldGetProductByIdFromDatabaseThroughHttp() throws Exception {
        mockMvc.perform(get("/api/product1/products/{id}", 900001L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(900001))
                .andExpect(jsonPath("$.productName").value("IT-P1-Keyboard"))
                .andExpect(jsonPath("$.categoryId").value(4))
                .andExpect(jsonPath("$.categoryName").value("办公外设"))
                .andExpect(jsonPath("$.price").value(329.00));
    }

    @Test
    void shouldCreateProductInDatabaseThroughHttp() throws Exception {
        mockMvc.perform(post("/api/product1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "productName": "IT-P1-New Mouse",
                                  "description": "created by integration test",
                                  "categoryId": 4,
                                  "price": 199.00,
                                  "stock": 20
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("success"));

        List<Product1> products = product1Mapper.findByProductNameContaining("IT-P1-New Mouse");

        assertEquals(1, products.size());
        assertNotNull(products.get(0).getId());
        assertEquals(4L, products.get(0).getCategoryId());
        assertEquals(1, products.get(0).getStatus());
    }

    @Test
    void shouldUpdateProductInDatabaseThroughHttp() throws Exception {
        mockMvc.perform(put("/api/product1/products/{id}", 900001L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "productName": "IT-P1-Keyboard-Updated",
                                  "description": "updated by integration test",
                                  "categoryId": 4,
                                  "price": 399.00,
                                  "stock": 30,
                                  "status": 1
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(900001))
                .andExpect(jsonPath("$.productName").value("IT-P1-Keyboard-Updated"));

        Product1 product = product1Mapper.findById(900001L);

        assertNotNull(product);
        assertEquals("IT-P1-Keyboard-Updated", product.getProductName());
        assertEquals(4L, product.getCategoryId());
        assertEquals(0, product.getPrice().compareTo(new java.math.BigDecimal("399.00")));
    }

    @Test
    void shouldDeleteProductInDatabaseThroughHttp() throws Exception {
        mockMvc.perform(delete("/api/product1/products/{id}", 900002L))
                .andExpect(status().isNoContent());

        assertNull(product1Mapper.findById(900002L));
        assertTrue(product1Mapper.findByProductNameContaining("IT-P1-Mouse").isEmpty());
    }
}
