package com.israel.api_store.customer.controller;

import com.israel.api_store.customer.dto.request.CreateCustomerRequestDto;
import com.israel.api_store.customer.dto.request.UpdateCustomerRequestDto;
import com.israel.api_store.customer.dto.response.CreateCustomerResponseDto;
import com.israel.api_store.customer.dto.response.CustomerResponseDto;
import com.israel.api_store.customer.dto.response.UpdateCustomerResponseDto;
import com.israel.api_store.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @PostMapping
    public ResponseEntity<CreateCustomerResponseDto> createCustomer(@RequestBody @Valid CreateCustomerRequestDto createCustomerRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(createCustomerRequestDto));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<UpdateCustomerResponseDto> updateCustomer(
            @PathVariable("customerId") Long customerId,
            @RequestBody @Valid UpdateCustomerRequestDto updateCustomerRequestDto) {
        return ResponseEntity.ok(customerService.updateCustomer(customerId, updateCustomerRequestDto));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}
