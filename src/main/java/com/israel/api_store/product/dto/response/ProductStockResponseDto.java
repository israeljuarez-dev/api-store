package com.israel.api_store.product.dto.response;

import lombok.Builder;

@Builder
public record ProductStockResponseDto(
        Long productCode,
        Double availableQuantity
) {
}
