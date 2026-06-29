package com.example.demo.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * ProductController 的返回结构测试。
 */
class ProductControllerTest {

    @Test
    void shouldWrapProductsWithCodeRecordsTotalAndMsg() {
        ProductService productService = mock(ProductService.class);
        ProductController controller = new ProductController(productService);
        List<Product> products = List.of(new Product(
                1L,
                "机械键盘 K87",
                "87 键机械键盘",
                new BigDecimal("329.00"),
                55,
                1,
                LocalDateTime.of(2026, 5, 29, 12, 0),
                LocalDateTime.of(2026, 5, 29, 12, 30)
        ));

        when(productService.findAll("键盘")).thenReturn(products);

        ProductListResponse response = controller.listProducts("键盘");

        verify(productService).findAll("键盘");
        assertEquals(200, response.code());
        assertEquals("success", response.msg());
        assertSame(products, response.records());
        assertEquals(1L, response.total());
    }
}
