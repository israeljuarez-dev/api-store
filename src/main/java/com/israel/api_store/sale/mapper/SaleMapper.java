package com.israel.api_store.sale.mapper;

import com.israel.api_store.sale.dto.response.CreateSaleResponseDto;
import com.israel.api_store.sale.dto.response.SaleProductResponseDto;
import com.israel.api_store.sale.dto.response.SaleResponseDto;
import com.israel.api_store.sale.dto.response.UpdateSaleResponseDto;
import com.israel.api_store.sale.model.Sale;
import com.israel.api_store.sale.model.SaleProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(source = "product.productCode", target = "productCode")
    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "product.brand", target = "brand")
    @Mapping(source = "product.cost", target = "cost")
    SaleProductResponseDto toSaleProductResponseDto(SaleProduct saleProduct);

    SaleResponseDto toSaleResponseDto(Sale sale);

    CreateSaleResponseDto toCreateSaleResponseDto(Sale sale);

    UpdateSaleResponseDto toUpdateSaleResponseDto(Sale sale);
}
