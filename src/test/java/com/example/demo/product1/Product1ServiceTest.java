package com.example.demo.product1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Product1Service 的最小单元测试。
 */
class Product1ServiceTest {

    @Test
    void shouldReturnAllProducts() {
        Product1Mapper mapper = mock(Product1Mapper.class);
        Product1Service service = new Product1Service(mapper);
        List<Product1> expected = List.of(sampleProduct(1L));

        when(mapper.findAll()).thenReturn(expected);

        assertSame(expected, service.findAll(null));
    }

    @Test
    void shouldSearchProductsByProductName() {
        Product1Mapper mapper = mock(Product1Mapper.class);
        Product1Service service = new Product1Service(mapper);
        List<Product1> expected = List.of(sampleProduct(1L));

        when(mapper.findByProductNameContaining("键盘")).thenReturn(expected);

        assertSame(expected, service.findAll("  键盘  "));
    }

    @Test
    void shouldReturnPagedProducts() {
        Product1Mapper mapper = mock(Product1Mapper.class);
        Product1Service service = new Product1Service(mapper);
        List<Product1> expected = List.of(sampleProduct(2L));

        when(mapper.count("键盘")).thenReturn(11L);
        when(mapper.findPage("键盘", 10, 10)).thenReturn(expected);

        Product1Page page = service.findPage("  键盘  ", 2, 10);

        assertSame(expected, page.records());
        assertEquals(11L, page.total());
        assertEquals(2, page.pageNum());
        assertEquals(10, page.pageSize());
    }

    @Test
    void shouldUseSafeDefaultPageArguments() {
        Product1Mapper mapper = mock(Product1Mapper.class);
        Product1Service service = new Product1Service(mapper);
        List<Product1> expected = List.of(sampleProduct(1L));

        when(mapper.count(null)).thenReturn(1L);
        when(mapper.findPage(null, 0, 10)).thenReturn(expected);

        Product1Page page = service.findPage(null, 0, 0);

        assertSame(expected, page.records());
        assertEquals(1, page.pageNum());
        assertEquals(10, page.pageSize());
    }

    @Test
    void shouldCreateProductWithDefaultStatus() {
        Product1Mapper mapper = mock(Product1Mapper.class);
        Product1Service service = new Product1Service(mapper);
        Product1Request request = new Product1Request(
                "机械键盘 K87",
                "87 键机械键盘",
                4L,
                new BigDecimal("329.00"),
                55,
                null
        );
        doAnswer(invocation -> {
            Product1 product = invocation.getArgument(0);
            product.setId(7L);
            return 1;
        }).when(mapper).insert(any(Product1.class));

        service.create(request);

        ArgumentCaptor<Product1> captor = ArgumentCaptor.forClass(Product1.class);
        verify(mapper).insert(captor.capture());
        assertEquals("机械键盘 K87", captor.getValue().getProductName());
        assertEquals(4L, captor.getValue().getCategoryId());
        assertEquals(1, captor.getValue().getStatus());
    }

    @Test
    void shouldUpdateExistingProduct() {
        Product1Mapper mapper = mock(Product1Mapper.class);
        Product1Service service = new Product1Service(mapper);
        Product1Request request = new Product1Request(
                "AirPods Pro 2",
                "主动降噪无线耳机",
                1L,
                new BigDecimal("1899.00"),
                30,
                1
        );
        Product1 updated = new Product1(
                6L,
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

        when(mapper.update(any(Product1.class))).thenReturn(1);
        when(mapper.findById(6L)).thenReturn(updated);

        Product1 actual = service.update(6L, request);

        ArgumentCaptor<Product1> captor = ArgumentCaptor.forClass(Product1.class);
        verify(mapper).update(captor.capture());
        assertEquals(6L, captor.getValue().getId());
        assertSame(updated, actual);
    }

    @Test
    void shouldThrowNotFoundWhenProductDoesNotExist() {
        Product1Mapper mapper = mock(Product1Mapper.class);
        Product1Service service = new Product1Service(mapper);

        when(mapper.findById(99L)).thenReturn(null);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> service.getById(99L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("99"));
    }

    @Test
    void shouldThrowNotFoundWhenDeleteMissingProduct() {
        Product1Mapper mapper = mock(Product1Mapper.class);
        Product1Service service = new Product1Service(mapper);

        when(mapper.deleteById(88L)).thenReturn(0);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> service.delete(88L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("88"));
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
