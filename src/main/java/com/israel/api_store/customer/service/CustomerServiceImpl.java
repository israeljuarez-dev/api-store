package com.israel.api_store.customer.service;

import com.israel.api_store.customer.dto.request.CreateCustomerRequestDto;
import com.israel.api_store.customer.dto.request.UpdateCustomerRequestDto;
import com.israel.api_store.customer.dto.response.CreateCustomerResponseDto;
import com.israel.api_store.customer.dto.response.CustomerResponseDto;
import com.israel.api_store.customer.dto.response.UpdateCustomerResponseDto;
import com.israel.api_store.customer.mapper.CustomerMapper;
import com.israel.api_store.customer.model.Customer;
import com.israel.api_store.customer.repository.CustomerRepository;
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
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDto getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    log.debug("No existe cliente con id: {}", customerId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with id: " + customerId);
                });

        return customerMapper.toCustomerResponseDto(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDto> getCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toCustomerResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public CreateCustomerResponseDto createCustomer(CreateCustomerRequestDto createCustomerRequestDto) {
        Customer customer = customerRepository.save(customerMapper.toCustomer(createCustomerRequestDto));
        log.info("Cliente registrado exitosamente con id: {}", customer.getCustomerId());
        return customerMapper.toCreateCustomerResponseDto(customer);
    }

    @Override
    @Transactional
    public UpdateCustomerResponseDto updateCustomer(Long customerId, UpdateCustomerRequestDto updateCustomerRequestDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    log.debug("No existe cliente con id: {}", customerId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with id: " + customerId);
                });

        customer.setName(updateCustomerRequestDto.name());
        customer.setLastName(updateCustomerRequestDto.lastName());
        customer.setDni(updateCustomerRequestDto.dni());

        Customer customerUpdated = customerRepository.save(customer);
        log.info("Cliente con id {} actualizado exitosamente", customerUpdated.getCustomerId());

        return customerMapper.toUpdateCustomerResponseDto(customerUpdated);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long customerId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    log.debug("No existe cliente con id: {}", customerId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with id: " + customerId);
                });

        customerRepository.deleteById(customerId);
        log.info("Cliente con id {} eliminado exitosamente", customerId);
    }
}