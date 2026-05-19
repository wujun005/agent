package com.example.demo.openai.api;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record GenerateResponseRequest(
        @NotBlank String input,
        String instructions,
        String previousResponseId,
        String model,
        @Positive Integer maxOutputTokens,
        @DecimalMin("0.0") @DecimalMax("2.0") Double temperature
) {
}
