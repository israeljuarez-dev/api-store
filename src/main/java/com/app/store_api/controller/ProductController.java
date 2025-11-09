package com.app.store_api.controller;

import com.app.store_api.controller.resource.ProductResource;
import com.app.store_api.dto.criteria.SearchProductCriteriaDTO;
import com.app.store_api.dto.product.ProductDTO;
import com.app.store_api.exception.ApiError;
import com.app.store_api.exception.StoreException;
import com.app.store_api.service.impl.ProductService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
@RequestMapping("/products")
public class ProductController implements ProductResource {

    static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(SearchProductCriteriaDTO criteriaDTO) {
        LOGGER.info("Fetching all products");
        List<ProductDTO> response = productService.getProducts(criteriaDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@Min(1) @PathVariable("id") UUID id) {
        LOGGER.info("Fetching product with ID {}", id);
        ProductDTO response = productService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @RateLimiter(name = "post-product", fallbackMethod = "fallBackPost")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody @Valid ProductDTO productDto) {
        LOGGER.info("Saving new product");
        ProductDTO response = productService.save(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@Min(1) @PathVariable("id") UUID id, @RequestBody @Valid ProductDTO productDto) {
        LOGGER.info("Updating product with ID {}", id);
        ProductDTO response = productService.update(id, productDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@Min(1) @PathVariable UUID id) {
        LOGGER.info("Deleting product with ID {}", id);
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<ProductDTO> fallBackPost() {
        LOGGER.debug("Fallback for saving product triggered");
        throw new StoreException(ApiError.EXCEED_NUMBER_REQUEST);
    }
}