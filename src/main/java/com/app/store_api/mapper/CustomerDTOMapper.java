package com.app.store_api.mapper;

import com.app.store_api.domain.Customer;
import com.app.store_api.dto.customer.CustomerDTO;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CustomerDTOMapper extends Converter<CustomerDTO, Customer> {

    @Override
    Customer convert(CustomerDTO source);
}
