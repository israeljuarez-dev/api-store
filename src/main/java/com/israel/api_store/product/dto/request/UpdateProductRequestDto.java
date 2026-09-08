package com.israel.api_store.product.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UpdateProductRequestDto(
        @NotBlank(message = "Name is required")
        @Size(max = 150, message = "Name must not exceed 150 characters")
        String name,

        @NotBlank(message = "Brand is required")
        @Size(max = 150, message = "Brand must not exceed 150 characters")
        String brand,

        @NotNull(message = "Cost is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Cost must be greater than 0")
        @Digits(integer = 8, fraction = 2, message = "Cost must have at most 8 integer digits and 2 decimal places")
        BigDecimal cost
) {
}
