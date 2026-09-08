package com.israel.api_store.sale.controller;

import com.israel.api_store.sale.dto.request.CreateSaleRequestDto;
import com.israel.api_store.sale.dto.request.UpdateSaleRequestDto;
import com.israel.api_store.sale.dto.response.CreateSaleResponseDto;
import com.israel.api_store.sale.dto.response.SaleResponseDto;
import com.israel.api_store.sale.dto.response.UpdateSaleResponseDto;
import com.israel.api_store.sale.service.SaleService;
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
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    @GetMapping("/{saleCode}")
    public ResponseEntity<SaleResponseDto> getSaleById(@PathVariable("saleCode") Long saleCode) {
        return ResponseEntity.ok(saleService.getSaleById(saleCode));
    }

    @GetMapping
    public ResponseEntity<List<SaleResponseDto>> getSales() {
        return ResponseEntity.ok(saleService.getSales());
    }

    @PostMapping
    public ResponseEntity<CreateSaleResponseDto> createSale(
            @RequestBody @Valid CreateSaleRequestDto createSaleRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.createSale(createSaleRequestDto));
    }

    @PutMapping("/{saleCode}")
    public ResponseEntity<UpdateSaleResponseDto> updateSale(
            @PathVariable("saleCode") Long saleCode,
            @RequestBody @Valid UpdateSaleRequestDto updateSaleRequestDto) {
        return ResponseEntity.ok(saleService.updateSale(saleCode, updateSaleRequestDto));
    }

    @DeleteMapping("/{saleCode}")
    public ResponseEntity<Void> deleteSale(@PathVariable("saleCode") Long saleCode) {
        saleService.deleteSale(saleCode);
        return ResponseEntity.noContent().build();
    }
}
