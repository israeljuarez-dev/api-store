package com.israel.api_store.customer.mapper;

import com.israel.api_store.customer.dto.request.CreateCustomerRequestDto;
import com.israel.api_store.customer.dto.response.CreateCustomerResponseDto;
import com.israel.api_store.customer.dto.response.CustomerResponseDto;
import com.israel.api_store.customer.dto.response.UpdateCustomerResponseDto;
import com.israel.api_store.customer.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerResponseDto toCustomerResponseDto(Customer customer);
    CreateCustomerResponseDto toCreateCustomerResponseDto(Customer customer);
    UpdateCustomerResponseDto toUpdateCustomerResponseDto(Customer customer);
    Customer toCustomer(CreateCustomerRequestDto createCustomerRequestDto);
}
