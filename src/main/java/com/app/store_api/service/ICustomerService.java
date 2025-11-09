package com.app.store_api.service;

import com.app.store_api.dto.customer.CustomerDTO;
import com.app.store_api.dto.criteria.SearchCustomerCriteriaDTO;

import java.util.List;
import java.util.UUID;

public interface ICustomerService {

    List<CustomerDTO> getCustomers(SearchCustomerCriteriaDTO criteriaDTO);

    CustomerDTO getById(UUID id);

    CustomerDTO save (CustomerDTO customerDTO);

    CustomerDTO update (UUID id, CustomerDTO customerDTO);

    void deleteById(UUID id);
}
