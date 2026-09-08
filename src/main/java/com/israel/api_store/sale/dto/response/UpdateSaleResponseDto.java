package com.israel.api_store.sale.dto.response;

import com.israel.api_store.customer.dto.response.CustomerResponseDto;
import com.israel.api_store.product.dto.response.ProductResponseDto;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record UpdateSaleResponseDto(
        Long saleCode,
        LocalDate saleDate,
        Double total,
        List<SaleProductResponseDto> saleProducts,
        CustomerResponseDto customer
) { }
