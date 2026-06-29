package com.example.demo.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * ProductService 的最小单元测试。
 */
class ProductServiceTest {

    @Test
    void shouldReturnAllProducts() {
        ProductRepository repository = mock(ProductRepository.class);
        ProductService service = new ProductService(repository);
        List<Product> expected = List.of(sampleProduct(1L));

        when(repository.findAll()).thenReturn(expected);

        assertSame(expected, service.findAll(null));
    }

    @Test
    void shouldSearchProductsByProductName() {
        ProductRepository repository = mock(ProductRepository.class);
        ProductService service = new ProductService(repository);
        List<Product> expected = List.of(sampleProduct(1L));

        when(repository.findByProductNameContaining("键盘")).thenReturn(expected);

        assertSame(expected, service.findAll("  键盘  "));
    }

    @Test
    void shouldCreateProductWithDefaultStatus() {
        ProductRepository repository = mock(ProductRepository.class);
        ProductService service = new ProductService(repository);
        ProductRequest request = new ProductRequest(
                "机械键盘 K87",
                "87 键机械键盘",
                new BigDecimal("329.00"),
                55,
                null
        );
        Product saved = sampleProduct(1L);

        when(repository.save(any(Product.class))).thenReturn(saved);

        Product actual = service.create(request);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(repository).save(captor.capture());
        assertEquals("机械键盘 K87", captor.getValue().productName());
        assertEquals(1, captor.getValue().status());
        assertSame(saved, actual);
    }

    @Test
    void shouldUpdateExistingProduct() {
        ProductRepository repository = mock(ProductRepository.class);
        ProductService service = new ProductService(repository);
        ProductRequest request = new ProductRequest(
                "AirPods Pro 2",
                "主动降噪无线耳机",
                new BigDecimal("1899.00"),
                30,
                1
        );
        Product updated = new Product(
                6L,
                "AirPods Pro 2",
                "主动降噪无线耳机",
                new BigDecimal("1899.00"),
                30,
                1,
                LocalDateTime.of(2026, 5, 29, 12, 0),
                LocalDateTime.of(2026, 5, 29, 12, 30)
        );

        when(repository.update(any(Product.class))).thenReturn(true);
        when(repository.findById(6L)).thenReturn(Optional.of(updated));

        Product actual = service.update(6L, request);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(repository).update(captor.capture());
        assertEquals(6L, captor.getValue().id());
        assertSame(updated, actual);
    }

    @Test
    void shouldThrowNotFoundWhenProductDoesNotExist() {
        ProductRepository repository = mock(ProductRepository.class);
        ProductService service = new ProductService(repository);

        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> service.getById(99L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("99"));
    }

    @Test
    void shouldThrowNotFoundWhenDeleteMissingProduct() {
        ProductRepository repository = mock(ProductRepository.class);
        ProductService service = new ProductService(repository);

        when(repository.deleteById(88L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> service.delete(88L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("88"));
    }

    private Product sampleProduct(Long id) {
        return new Product(
                id,
                "机械键盘 K87",
                "87 键机械键盘",
                new BigDecimal("329.00"),
                55,
                1,
                LocalDateTime.of(2026, 5, 29, 12, 0),
                LocalDateTime.of(2026, 5, 29, 12, 30)
        );
    }
}
