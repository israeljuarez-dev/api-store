package com.app.store_api.service;

import com.app.store_api.dto.criteria.SearchProductCriteriaDTO;
import com.app.store_api.dto.product.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface IProductService {

    List<ProductDTO> getProducts(SearchProductCriteriaDTO criteriaDTO);

    ProductDTO getById(UUID id);

    ProductDTO save (ProductDTO productDTO);

    ProductDTO update (UUID id, ProductDTO productDTO);

    void deleteById(UUID id);
}
