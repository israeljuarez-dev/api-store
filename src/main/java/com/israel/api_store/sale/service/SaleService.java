package com.israel.api_store.sale.service;

import com.israel.api_store.sale.dto.request.CreateSaleRequestDto;
import com.israel.api_store.sale.dto.request.UpdateSaleRequestDto;
import com.israel.api_store.sale.dto.response.CreateSaleResponseDto;
import com.israel.api_store.sale.dto.response.SaleResponseDto;
import com.israel.api_store.sale.dto.response.UpdateSaleResponseDto;

import java.util.List;

public interface SaleService {
    SaleResponseDto getSaleById(Long saleCode);

    List<SaleResponseDto> getSales();

    CreateSaleResponseDto createSale(CreateSaleRequestDto createSaleRequestDto);

    UpdateSaleResponseDto updateSale(Long saleCode, UpdateSaleRequestDto updateSaleRequestDto);

    void deleteSale(Long saleCode);

    //

}
