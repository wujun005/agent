package com.example.demo.sensitive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 验证敏感词异常对应的 HTTP 返回是否正确。
 */
class ApiExceptionHandlerTest {

    /**
     * 处理器应返回稳定的 400 错误结构，供前端统一处理。
     */
    @Test
    void shouldReturnBadRequestForSensitiveWordException() {
        ApiExceptionHandler handler = new ApiExceptionHandler();

        ResponseEntity<ApiErrorResponse> response =
                handler.handleSensitiveWordBlocked(new SensitiveWordBlockedException(java.util.List.of("刷单")));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("SENSITIVE_WORD_BLOCKED", response.getBody().code());
        assertEquals("Sensitive content detected in request", response.getBody().message());
        assertEquals(java.util.List.of("刷单"), response.getBody().sensitiveWords());
    }
}
