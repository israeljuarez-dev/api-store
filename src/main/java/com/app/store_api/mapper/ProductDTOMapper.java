package com.app.store_api.mapper;

import com.app.store_api.domain.Product;
import com.app.store_api.dto.product.ProductDTO;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface ProductDTOMapper extends Converter<ProductDTO, Product> {

    @Override
    Product convert(ProductDTO source);
}
