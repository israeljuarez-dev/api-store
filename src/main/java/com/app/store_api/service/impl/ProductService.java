package com.app.store_api.service.impl;

import com.app.store_api.domain.Product;
import com.app.store_api.dto.criteria.SearchProductCriteriaDTO;
import com.app.store_api.dto.product.ProductDTO;
import com.app.store_api.exception.ApiError;
import com.app.store_api.exception.StoreException;
import com.app.store_api.persistence.repository.IProductRepository;
import com.app.store_api.persistence.specification.ProductSpecification;
import com.app.store_api.service.IProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService implements IProductService {

    IProductRepository productRepository;

    ConversionService conversionService;

    @Transactional(readOnly = true)
    @Override
    public List<ProductDTO> getProducts(SearchProductCriteriaDTO criteriaDTO) {
        log.info("Fetching list of products by criteria: {}", criteriaDTO);

        Pageable pageable = PageRequest.of(criteriaDTO.getPageActual(), criteriaDTO.getPageSize());
        List<Product> products = productRepository.findAll(ProductSpecification.withSearchCriteria(criteriaDTO), pageable);

        if (products.isEmpty()) {
            log.warn("Products list is empty for criteria: {}", criteriaDTO);
            return Collections.emptyList();
        }

        log.debug("Products fetched successfully with size: {}", products.size());
        return products.stream()
                .map(product -> conversionService.convert(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDTO getById(UUID id) {
        log.info("Fetching product by ID: {}", id);

        Product foundProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product with ID {} not found.", id);
                    return new StoreException(ApiError.PRODUCT_NOT_FOUND);
                });

        log.info("Product with ID {} found successfully.", id);
        return conversionService.convert(foundProduct, ProductDTO.class);
    }

    @Transactional
    @Override
    public ProductDTO save(ProductDTO productDTO) {
        log.info("Saving new product");

        Product product = conversionService.convert(productDTO, Product.class);
        if (product == null) {
            log.error("Conversion from ProductDTO to Product entity failed.");
            throw new StoreException(ApiError.PRODUCT_CONVERSION_FAILED);
        }

        productRepository.save(product);
        log.info("Product saved successfully.");

        return conversionService.convert(product, ProductDTO.class);
    }

    @Transactional
    @Override
    public ProductDTO update(UUID id, ProductDTO productDTO) {
        log.info("Updating product with ID: {}", id);

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product with ID {} not found for update.", id);
                    return new StoreException(ApiError.PRODUCT_NOT_FOUND);
                });

        existingProduct.setName(productDTO.name());
        existingProduct.setTradeMark(productDTO.tradeMark());
        existingProduct.setPrice(productDTO.price());
        existingProduct.setDescription(productDTO.description());

        Product updatedProduct = productRepository.save(existingProduct);
        log.info("Product with ID {} updated successfully.", id);

        return conversionService.convert(updatedProduct, ProductDTO.class);
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        log.info("Deleting product with ID: {}", id);

        if (!productRepository.existsById(id)) {
            log.warn("Product with ID {} not found. Cannot delete.", id);
            throw new StoreException(ApiError.PRODUCT_NOT_FOUND);
        }

        productRepository.deleteById(id);
        log.debug("Product with ID {} deleted successfully.", id);
    }

    /*
    Manejo de Stock:
    - Aumentar (actualizar) stock cuando ingresan productos (compra al proveedor o reposición)
    - Disminuir (actualizar) stock cuando se vende o reserva un producto
    - Validar stock disponible antes de realizar una operación (por ejemplo, no permitir ventas con stock negativo)
    -> Tecnicamente será un endpoint que actualizará el stock dependiendo el body de json, usaremos un enum que determine
       cada tipo de operacion
    */

    // Obtener todos los productos cuya cantidad_disponible sea menor a 5 - localhost:8080/api/v1/products/missing_stock

}
