package com.israel.api_store.sale.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SaleProductResponseDto(
        Long productCode,
        String name,
        String brand,
        BigDecimal cost,
        Double quantity
) {
}
