package com.israel.api_store.product.dto.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateProductRequestDto(
        String name,
        String brand,
        BigDecimal cost,
        Double availableQuantity
) {
}
