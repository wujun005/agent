package com.example.demo.openai.api;

public record GenerateResponseResult(
        String responseId,
        String model,
        String outputText,
        Long inputTokens,
        Long outputTokens,
        Long totalTokens
) {
}
