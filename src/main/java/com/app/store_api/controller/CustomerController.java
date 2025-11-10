package com.app.store_api.controller;

import com.app.store_api.controller.resource.CustomerResource;
import com.app.store_api.dto.customer.CustomerDTO;
import com.app.store_api.dto.criteria.SearchCustomerCriteriaDTO;
import com.app.store_api.exception.ApiError;
import com.app.store_api.exception.StoreException;
import com.app.store_api.service.ICustomerService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
@RequestMapping("/customers")
public class CustomerController implements CustomerResource {

    static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    ICustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getCustomers(SearchCustomerCriteriaDTO criteriaDTO) {
        LOGGER.info("Obtain all the customers");
        List<CustomerDTO> response = customerService.getCustomers(criteriaDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@Min(1) @PathVariable("id") UUID id) {
        LOGGER.info("Obtain information from a customer with {}", id);
        CustomerDTO response = customerService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @RateLimiter(name = "post-customer", fallbackMethod = "fallBackPost")
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        LOGGER.info("Saving new customer");
        CustomerDTO response = customerService.save(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@Min(1) @PathVariable("id") UUID id, @RequestBody @Valid CustomerDTO customerDTO) {
        LOGGER.info("Updating a customer with {}", id);
        CustomerDTO response = customerService.update(id, customerDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@Min(1) @PathVariable UUID id) {
        LOGGER.info("Deleting a customer with {}", id);
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<CustomerDTO> fallBackPost() {
        LOGGER.debug("calling to fallbackPost");
        throw new StoreException(ApiError.EXCEED_NUMBER_REQUEST);
    }
}
