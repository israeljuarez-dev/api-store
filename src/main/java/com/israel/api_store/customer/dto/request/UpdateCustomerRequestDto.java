package com.israel.api_store.customer.dto.request;

import lombok.Builder;

@Builder
public record UpdateCustomerRequestDto(
        String name,
        String lastName,
        String dni
) {
}
