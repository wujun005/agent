package com.example.demo.openai.api;

/**
 * 模型执行后返回给调用方的简化结果。
 */
public record GenerateResponseResult(
        String responseId,
        String model,
        String outputText,
        Long inputTokens,
        Long outputTokens,
        Long totalTokens
) {
}
