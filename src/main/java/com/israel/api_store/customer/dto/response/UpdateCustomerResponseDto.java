package com.israel.api_store.customer.dto.response;

import lombok.Builder;

@Builder
public record UpdateCustomerResponseDto(
        Long customerId,
        String name,
        String lastName,
        String dni
) {
}
