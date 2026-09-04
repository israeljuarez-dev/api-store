package com.israel.api_store.product.dto.request;

import lombok.Builder;

@Builder
public record StockQuantityRequestDto(
        Double quantity
) {
}
