package com.israel.api_store.customer.dto.request;

import lombok.Builder;

@Builder
public record CreateCustomerRequestDto(
        String name,
        String lastName,
        String dni
) {
}
