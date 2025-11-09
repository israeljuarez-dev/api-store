package com.app.store_api.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CustomerDTO(

        @JsonProperty("customer_id")
        @Null(message = "Customer ID must be null; it is generated automatically")
        UUID id,

        @NotBlank(message = "Name must not be blank")
        @Size(max = 50, message = "Name must not exceed 50 characters")
        String name,

        @JsonProperty("last_name")
        @NotBlank(message = "Last name must not be blank")
        @Size(max = 80, message = "Last name must not exceed 80 characters")
        String lastName,

        @NotBlank(message = "DNI must not be blank")
        @Size(max = 12, message = "DNI must not exceed 12 characters")
        String dni
) {
}
