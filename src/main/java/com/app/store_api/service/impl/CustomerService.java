package com.app.store_api.service.impl;

import com.app.store_api.domain.Customer;
import com.app.store_api.dto.customer.CustomerDTO;
import com.app.store_api.dto.criteria.SearchCustomerCriteriaDTO;
import com.app.store_api.exception.ApiError;
import com.app.store_api.exception.StoreException;
import com.app.store_api.persistence.repository.ICustomerRepository;
import com.app.store_api.persistence.specification.CustomerSpecification;
import com.app.store_api.service.ICustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerService implements ICustomerService {

    ICustomerRepository customerRepository;

    ConversionService conversionService;

    @Transactional(readOnly = true)
    @Override
    public List<CustomerDTO> getCustomers(SearchCustomerCriteriaDTO criteriaDto) {
        log.info("Fetching list of customers by criteria: {}", criteriaDto);

        Pageable pageable = PageRequest.of(criteriaDto.getPageActual(), criteriaDto.getPageSize());

        List<Customer> customers = customerRepository.findAll(CustomerSpecification.withSearchCriteria(criteriaDto), pageable);
        if (customers.isEmpty()) {
            log.warn("Customers are empty list.");
            return Collections.emptyList();
        }
        log.debug("Customers with fetched successfully.");

        return customers.stream()
                .map(customer -> conversionService.convert(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerDTO getById(UUID id) {
        log.info("Fetching customer by id: {}", id);

        Customer foundCustomer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Customer with id: {} not exists", id);
                    return new StoreException(ApiError.CUSTOMER_NOT_FOUND);
                });
        log.info("Customer with id {} was found.", id);

        return conversionService.convert(foundCustomer, CustomerDTO.class);
    }

    @Transactional
    @Override
    public CustomerDTO save(CustomerDTO customerDto) {
        log.info("Saving customer");

        Customer customer = conversionService.convert(customerDto, Customer.class);
        if (customer == null) {
            log.error("Conversion from CustomerDTO to Customer failed");
            throw new StoreException(ApiError.CUSTOMER_CONVERSION_FAILED);
        }

        customerRepository.save(customer);
        log.info("Customer saved");

        return conversionService.convert(customer, CustomerDTO.class);
    }

    @Transactional
    @Override
    public CustomerDTO update(UUID id, CustomerDTO customerDTO) {
        log.info("Updating customer with id: {}", id);

        Customer foundCustomer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Customer with id {} not found for update.", id);
                    return new StoreException(ApiError.CUSTOMER_NOT_FOUND);
                });

        foundCustomer.setName(customerDTO.name());
        foundCustomer.setLastName(customerDTO.lastName());

        Customer customerUpdated = customerRepository.save(foundCustomer);
        log.info("Customer with ID {} updated successfully.", id);

        return conversionService.convert(customerUpdated, CustomerDTO.class);
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        log.info("Deleting customer with id: {}", id);

        if (!customerRepository.existsById(id)) {
            log.warn("Customer with ID {} not found. Cannot delete.", id);
            throw new StoreException(ApiError.CUSTOMER_NOT_FOUND);
        }

        customerRepository.deleteById(id);
        log.debug("Customer with ID {} deleted successfully.", id);
    }
}
