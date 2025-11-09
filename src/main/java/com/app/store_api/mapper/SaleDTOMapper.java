package com.app.store_api.mapper;

import com.app.store_api.domain.Sale;
import com.app.store_api.dto.sale.SaleDTO;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface SaleDTOMapper extends Converter<SaleDTO, Sale> {

    @Override
    Sale convert(SaleDTO source);
}
