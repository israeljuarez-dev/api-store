package com.israel.api_store.product.mapper;

import com.israel.api_store.product.dto.request.CreateProductRequestDto;
import com.israel.api_store.product.dto.response.CreateProductResponseDto;
import com.israel.api_store.product.dto.response.ProductResponseDto;
import com.israel.api_store.product.dto.response.ProductStockResponseDto;
import com.israel.api_store.product.dto.response.UpdateProductResponseDto;
import com.israel.api_store.product.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDto toProductResponseDto(Product product);

    CreateProductResponseDto toCreateProductResponseDto(Product product);

    Product toProduct(CreateProductRequestDto createProductRequestDto);

    UpdateProductResponseDto toUpdateProductResponseDto(Product product);

    ProductStockResponseDto toProductStockResponseDto(Product product);
}
