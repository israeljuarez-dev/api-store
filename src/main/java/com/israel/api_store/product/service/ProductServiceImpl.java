package com.israel.api_store.product.service;

import com.israel.api_store.product.dto.request.CreateProductRequestDto;
import com.israel.api_store.product.dto.request.UpdateProductRequestDto;
import com.israel.api_store.product.dto.response.CreateProductResponseDto;
import com.israel.api_store.product.dto.response.ProductResponseDto;
import com.israel.api_store.product.dto.response.ProductStockResponseDto;
import com.israel.api_store.product.dto.response.UpdateProductResponseDto;
import com.israel.api_store.product.mapper.ProductMapper;
import com.israel.api_store.product.model.Product;
import com.israel.api_store.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProductByCode(Long productCode) {
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> {
                    log.debug("No existe producto con código: {}", productCode);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with code:" + productCode);
                });

        return productMapper.toProductResponseDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public CreateProductResponseDto createProduct(CreateProductRequestDto createProductRequestDto) {
        Product product = productRepository.save(productMapper.toProduct(createProductRequestDto));
        log.info("Producto registrado exitosamente con código {}",  product.getProductCode());
        return productMapper.toCreateProductResponseDto(product);
    }

    @Override
    @Transactional
    public UpdateProductResponseDto updateProduct(Long productCode, UpdateProductRequestDto updateProductRequestDto) {
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> {
                    log.debug("No existe producto con código: {}", productCode);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with code:" + productCode);
                });

        product.setName(updateProductRequestDto.name());
        product.setBrand(updateProductRequestDto.brand());
        product.setCost(updateProductRequestDto.cost());

        Product productUpdated = productRepository.save(product);

        log.info("Producto con código {} actualizado exitosamente", productUpdated.getProductCode());

        return productMapper.toUpdateProductResponseDto(productUpdated);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productCode) {
        productRepository.deleteById(productCode);
        log.info("Producto con código {} eliminado exitosamente", productCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsWithLowStock() {
        return productRepository.findProductsWithLowStock().stream()
                .map(productMapper::toProductResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public ProductStockResponseDto decreaseStock(Long productCode, Double quantity) {
        int affectedRows = productRepository.decreaseStock(productCode, quantity);

        if (affectedRows == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with code: "
                    + productCode);
        }

        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with code: "
                        + productCode));

        log.info("Stock disminuido exitosamente del producto: {}", product.getName());

        return productMapper.toProductStockResponseDto(product);
    }

    @Override
    @Transactional
    public ProductStockResponseDto increaseStock(Long productCode, Double quantity) {
        int affectedRows = productRepository.increaseStock(productCode, quantity);

        if (affectedRows == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with code: "
                    + productCode);
        }

        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with code: "
                        + productCode));

        log.info("Stock aumentado exitosamente del producto: {}", product.getName());

        return productMapper.toProductStockResponseDto(product);
    }
}
