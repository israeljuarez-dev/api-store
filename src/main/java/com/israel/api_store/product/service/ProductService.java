package com.israel.api_store.product.service;

import com.israel.api_store.product.dto.request.CreateProductRequestDto;
import com.israel.api_store.product.dto.request.UpdateProductRequestDto;
import com.israel.api_store.product.dto.response.CreateProductResponseDto;
import com.israel.api_store.product.dto.response.ProductResponseDto;
import com.israel.api_store.product.dto.response.ProductStockResponseDto;
import com.israel.api_store.product.dto.response.UpdateProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto getProductByCode(Long productCode);

    List<ProductResponseDto> getProducts();

    CreateProductResponseDto createProduct(CreateProductRequestDto createProductRequestDto);

    UpdateProductResponseDto updateProduct(Long productCode, UpdateProductRequestDto updateProductRequestDto);

    void deleteProduct(Long productCode);

    List<ProductResponseDto> getProductsWithLowStock();

    ProductStockResponseDto decreaseStock(Long productCode, Double quantity);

    ProductStockResponseDto increaseStock(Long productCode, Double quantity);
}
