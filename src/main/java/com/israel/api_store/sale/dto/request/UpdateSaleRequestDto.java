package com.israel.api_store.sale.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record UpdateSaleRequestDto(
        @NotNull(message = "Customer id is required")
        Long customerId,

        @NotNull(message = "Product list is required")
        @Size(min = 1, message = "Sale must have at least one product")
        List<@Valid SaleProductRequestDto> products
) { }