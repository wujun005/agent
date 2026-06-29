package com.example.demo.sensitive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 将领域异常转换为稳定的 HTTP 错误响应。
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * 当请求命中敏感词时，返回 400 错误。
     */
    @ExceptionHandler(SensitiveWordBlockedException.class)
    public ResponseEntity<ApiErrorResponse> handleSensitiveWordBlocked(SensitiveWordBlockedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(
                        "SENSITIVE_WORD_BLOCKED",
                        ex.getMessage(),
                        ex.getSensitiveWords()
                ));
    }
}
