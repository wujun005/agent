package com.example.demo.sensitive;

import java.util.List;

/**
 * 敏感词拦截时返回的统一错误结构。
 */
public record ApiErrorResponse(
        String code,
        String message,
        List<String> sensitiveWords
) {
}
