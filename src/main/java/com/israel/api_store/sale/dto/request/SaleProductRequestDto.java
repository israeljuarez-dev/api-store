package com.israel.api_store.sale.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SaleProductRequestDto(
        @NotNull(message = "Product code is required")
        Long productCode,

        @NotNull(message = "Quantity is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Quantity must be greater than 0")
        Double quantity
) { }
