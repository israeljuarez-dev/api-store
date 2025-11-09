package com.app.store_api.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record ProductDTO(

        @JsonProperty("product_id")
        @Null(message = "Product ID must be null; it is generated automatically")
        UUID id,

        @NotBlank(message = "Name must not be blank")
        @Size(max = 50, message = "Name must not exceed 50 characters")
        String name,

        @JsonProperty("trade_mark")
        @NotBlank(message = "Trade mark must not be blank")
        @Size(max = 50, message = "Trade mark must not exceed 50 characters")
        String tradeMark,

        @NotNull(message = "Price must not be null")
        @DecimalMin(value = "0.1", inclusive = false, message = "Price must be greater than 0")
        BigDecimal price,

        @NotBlank(message = "Name must not be blank")
        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description
) {
}
