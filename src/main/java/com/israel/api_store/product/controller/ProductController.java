package com.israel.api_store.product.controller;

import com.israel.api_store.product.dto.request.CreateProductRequestDto;
import com.israel.api_store.product.dto.request.StockQuantityRequestDto;
import com.israel.api_store.product.dto.request.UpdateProductRequestDto;
import com.israel.api_store.product.dto.response.CreateProductResponseDto;
import com.israel.api_store.product.dto.response.ProductResponseDto;
import com.israel.api_store.product.dto.response.ProductStockResponseDto;
import com.israel.api_store.product.dto.response.UpdateProductResponseDto;
import com.israel.api_store.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productCode}")
    public ResponseEntity<ProductResponseDto> getProductByCode(@PathVariable("productCode") Long productCode){
        ProductResponseDto productResponseDto = productService.getProductByCode(productCode);
        return ResponseEntity.ok(productResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @PostMapping
    public ResponseEntity<CreateProductResponseDto> createProduct(@RequestBody @Valid CreateProductRequestDto createProductRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(createProductRequestDto));
    }

    @PutMapping("/{productCode}")
    public ResponseEntity<UpdateProductResponseDto> updateProduct(
            @PathVariable("productCode") Long productCode,
            @RequestBody @Valid UpdateProductRequestDto updateProductRequestDto
    ) {
        return ResponseEntity.ok(productService.updateProduct(productCode, updateProductRequestDto));
    }

    @DeleteMapping("/delete/{productCode}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productCode") Long productCode) {
        productService.deleteProduct(productCode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductResponseDto>> getProductsWithLowStock() {
        return ResponseEntity.ok(productService.getProductsWithLowStock());
    }

    @PatchMapping("/stock/decrease/{productCode}")
    public ResponseEntity<ProductStockResponseDto> decreaseStock(
            @PathVariable("productCode") Long productCode,
            @RequestBody @Valid StockQuantityRequestDto stockQuantityRequestDto) {
        return ResponseEntity.ok(productService.decreaseStock(productCode, stockQuantityRequestDto.quantity()));
    }

    @PatchMapping("/stock/increase/{productCode}")
    public ResponseEntity<ProductStockResponseDto> increaseStock(
            @PathVariable("productCode") Long productCode,
            @RequestBody @Valid StockQuantityRequestDto stockQuantityRequestDto) {
        return ResponseEntity.ok(productService.increaseStock(productCode, stockQuantityRequestDto.quantity()));
    }
}
