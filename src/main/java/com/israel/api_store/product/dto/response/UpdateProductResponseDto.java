package com.israel.api_store.product.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UpdateProductResponseDto(
        Long productCode,
        String name,
        String brand,
        BigDecimal cost
) {
}
