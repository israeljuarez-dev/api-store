package com.app.store_api.service;

import com.app.store_api.dto.sale.SaleDTO;
import com.app.store_api.dto.criteria.SearchSaleCriteriaDTO;

import java.util.List;
import java.util.UUID;

public interface ISaleService {

    List<SaleDTO> getSales(SearchSaleCriteriaDTO criteriaDTO);

    SaleDTO getById(UUID id);

    SaleDTO save (SaleDTO saleDTO);

    SaleDTO update (UUID id, SaleDTO saleDTO);

    SaleDTO deleteById(UUID id);
}
