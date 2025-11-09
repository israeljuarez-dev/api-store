package com.app.store_api.mapper;

import com.app.store_api.domain.Product;
import com.app.store_api.dto.product.ProductDTO;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface ProductMapper extends Converter<Product, ProductDTO> {

    @Override
    ProductDTO convert(Product source);
}