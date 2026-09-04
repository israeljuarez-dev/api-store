package com.israel.api_store.product.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponseDto(
        Long productCode,
        String name,
        String brand,
        BigDecimal cost,
        Double availableQuantity
) {
}
