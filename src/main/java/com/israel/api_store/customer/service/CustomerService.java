package com.israel.api_store.customer.service;

import com.israel.api_store.customer.dto.request.CreateCustomerRequestDto;
import com.israel.api_store.customer.dto.request.UpdateCustomerRequestDto;
import com.israel.api_store.customer.dto.response.CreateCustomerResponseDto;
import com.israel.api_store.customer.dto.response.CustomerResponseDto;
import com.israel.api_store.customer.dto.response.UpdateCustomerResponseDto;

import java.util.List;

public interface CustomerService {
    CustomerResponseDto getCustomerById(Long customerId);

    List<CustomerResponseDto> getCustomers();

    CreateCustomerResponseDto createCustomer(CreateCustomerRequestDto createCustomerRequestDto);

    UpdateCustomerResponseDto updateCustomer(Long customerId, UpdateCustomerRequestDto updateCustomerRequestDto);

    void deleteCustomer(Long customerId);
}
