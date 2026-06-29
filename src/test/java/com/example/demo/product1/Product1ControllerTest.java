package com.example.demo.product1;

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
 * Product1Controller 的返回结构测试。
 */
class Product1ControllerTest {

    @Test
    void shouldWrapProductsWithCodeRecordsTotalAndMsg() {
        Product1Service product1Service = mock(Product1Service.class);
        Product1Controller controller = new Product1Controller(product1Service);
        List<Product1> products = List.of(new Product1(
                1L,
                "机械键盘 K87",
                "87 键机械键盘",
                4L,
                "办公外设",
                new BigDecimal("329.00"),
                55,
                1,
                LocalDateTime.of(2026, 5, 29, 12, 0),
                LocalDateTime.of(2026, 5, 29, 12, 30)
        ));
        Product1Page page = new Product1Page(products, 11L, 2, 10);

        when(product1Service.findPage("键盘", 2, 10)).thenReturn(page);

        Product1ListResponse response = controller.listProducts("键盘", 2, 10);

        verify(product1Service).findPage("键盘", 2, 10);
        assertEquals(200, response.code());
        assertEquals("success", response.msg());
        assertSame(products, response.records());
        assertEquals(11L, response.total());
        assertEquals(2, response.pageNum());
        assertEquals(10, response.pageSize());
        assertEquals(2L, response.pages());
    }
}
