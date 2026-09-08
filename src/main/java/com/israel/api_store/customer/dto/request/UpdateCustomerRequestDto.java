package com.israel.api_store.customer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UpdateCustomerRequestDto(
        @NotBlank(message = "Name is required")
        @Size(max = 150, message = "Name must not exceed 150 characters")
        String name,

        @NotBlank(message = "Last name is required")
        @Size(max = 150, message = "Last name must not exceed 150 characters")
        String lastName,

        @NotBlank(message = "DNI is required")
        @Size(max = 20, message = "DNI must not exceed 20 characters")
        String dni
) {
}
