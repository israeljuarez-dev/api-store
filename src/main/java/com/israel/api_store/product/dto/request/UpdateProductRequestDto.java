package com.israel.api_store.product.dto.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UpdateProductRequestDto(
        String name,
        String brand,
        BigDecimal cost
) {
}
