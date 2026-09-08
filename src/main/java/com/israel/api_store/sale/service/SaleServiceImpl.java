package com.israel.api_store.sale.service;

import com.israel.api_store.customer.model.Customer;
import com.israel.api_store.customer.repository.CustomerRepository;
import com.israel.api_store.product.model.Product;
import com.israel.api_store.product.repository.ProductRepository;
import com.israel.api_store.sale.dto.request.CreateSaleRequestDto;
import com.israel.api_store.sale.dto.request.SaleProductRequestDto;
import com.israel.api_store.sale.dto.request.UpdateSaleRequestDto;
import com.israel.api_store.sale.dto.response.CreateSaleResponseDto;
import com.israel.api_store.sale.dto.response.SaleResponseDto;
import com.israel.api_store.sale.dto.response.UpdateSaleResponseDto;
import com.israel.api_store.sale.mapper.SaleMapper;
import com.israel.api_store.sale.model.Sale;
import com.israel.api_store.sale.model.SaleProduct;
import com.israel.api_store.sale.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    private final ProductRepository productRepository;

    private final CustomerRepository customerRepository;

    private final SaleMapper saleMapper;

    @Override
    @Transactional(readOnly = true)
    public SaleResponseDto   getSaleById(Long saleCode) {
        return saleMapper.toSaleResponseDto(findSaleById(saleCode));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleResponseDto> getSales() {
        return saleRepository.findAll().stream()
                .map(saleMapper::toSaleResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public CreateSaleResponseDto createSale(CreateSaleRequestDto createSaleRequestDto) {
        Customer customer = findCustomerById(createSaleRequestDto.customerId());

        List<Product> products = findAndValidateProducts(createSaleRequestDto.products());

        createSaleRequestDto.products().forEach(saleProduct -> {
            Product product = findProductByCodeInList(products, saleProduct.productCode());
            validateSufficientStock(product, saleProduct.quantity());
        });

        BigDecimal total = calculateTotal(createSaleRequestDto.products(), products);

        Sale sale = Sale.builder()
                .saleDate(LocalDate.now(ZoneId.of("America/Lima")))
                .total(total)
                .customer(customer)
                .saleProducts(new ArrayList<>())
                .build();

        List<SaleProduct> saleProducts = createSaleRequestDto.products().stream()
                .map(saleProductRequest -> {
                    Product product = findProductByCodeInList(products, saleProductRequest.productCode());
                    productRepository.decreaseStock(product.getProductCode(), saleProductRequest.quantity());
                    return SaleProduct.builder()
                            .sale(sale)
                            .product(product)
                            .quantity(saleProductRequest.quantity())
                            .build();
                })
                .toList();

        sale.setSaleProducts(saleProducts);

        Sale savedSale = saleRepository.save(sale);
        log.info("Venta registrada exitosamente con código: {}", savedSale.getSaleCode());

        return saleMapper.toCreateSaleResponseDto(savedSale);
    }

    @Override
    @Transactional
    public UpdateSaleResponseDto updateSale(Long saleCode, UpdateSaleRequestDto updateSaleRequestDto) {
        Sale sale = findSaleById(saleCode);

        Customer customer = findCustomerById(updateSaleRequestDto.customerId());

        List<Product> products = findAndValidateProducts(updateSaleRequestDto.products());

        List<Long> newProductCodes = updateSaleRequestDto.products().stream()
                .map(SaleProductRequestDto::productCode)
                .toList();

        restoreStockForRemovedProducts(sale, newProductCodes);

        adjustStockForUpdatedProducts(sale, updateSaleRequestDto.products(), products);

        BigDecimal total = calculateTotal(updateSaleRequestDto.products(), products);

        List<SaleProduct> updatedSaleProducts = updateSaleRequestDto.products().stream()
                .map(saleProductRequest -> {
                    Product product = findProductByCodeInList(products, saleProductRequest.productCode());
                    return SaleProduct.builder()
                            .sale(sale)
                            .product(product)
                            .quantity(saleProductRequest.quantity())
                            .build();
                })
                .toList();

        sale.setCustomer(customer);
        sale.setTotal(total);
        sale.getSaleProducts().clear();
        sale.getSaleProducts().addAll(updatedSaleProducts);

        Sale updatedSale = saleRepository.save(sale);
        log.info("Venta con código {} actualizada exitosamente", updatedSale.getSaleCode());

        return saleMapper.toUpdateSaleResponseDto(updatedSale);
    }

    @Override
    @Transactional
    public void deleteSale(Long saleCode) {
        saleRepository.findById(saleCode)
                .orElseThrow(() -> {
                    log.debug("No existe venta con código: {}", saleCode);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found with code: " + saleCode);
                });

        saleRepository.deleteById(saleCode);
        log.info("Venta con código {} eliminada exitosamente", saleCode);
    }

    // -------------------- MÉTODOS PRIVADOS ---------------------//

    private Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    log.debug("No existe cliente con id: {}", customerId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with id: " + customerId);
                });
    }

    private List<Product> findAndValidateProducts(List<SaleProductRequestDto> saleProductRequests) {
        List<Long> productCodes = saleProductRequests.stream()
                .map(SaleProductRequestDto::productCode)
                .toList();

        List<Product> products = productRepository.findAllById(productCodes);

        if (products.size() != productCodes.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more products not found");
        }

        return products;
    }

    private void validateSufficientStock(Product product, Double saleProductQuantity){
        if (product.getAvailableQuantity() < saleProductQuantity){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Insufficient stock for product: " + product.getName()
                    + ". Available: " + product.getAvailableQuantity()
                    + ", requested: " + saleProductQuantity
            );
        }
    }

    private BigDecimal calculateTotal(List<SaleProductRequestDto> saleProductRequests, List<Product> products){
        return saleProductRequests.stream()
                .map(saleProductRequest -> {
                    Product product = findProductByCodeInList(products, saleProductRequest.productCode());
                    return product.getCost().multiply(BigDecimal.valueOf(saleProductRequest.quantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Product findProductByCodeInList(List<Product> products, Long productCode) {
        return products.stream()
                .filter(p -> p.getProductCode().equals(productCode))
                .findFirst()
                .orElseThrow();
    }

    private Sale findSaleById(Long saleCode) {
        return saleRepository.findById(saleCode)
                .orElseThrow(() -> {
                    log.debug("No existe venta con código: {}", saleCode);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found with code: " + saleCode);
                });
    }

    private void restoreStockForRemovedProducts(Sale sale, List<Long> newProductCodes) {
        sale.getSaleProducts().forEach(previousSaleProduct -> {
            boolean isStillInSale = newProductCodes.contains(previousSaleProduct.getProduct().getProductCode());
            if (!isStillInSale) {
                productRepository.increaseStock(
                        previousSaleProduct.getProduct().getProductCode(),
                        previousSaleProduct.getQuantity()
                );
                log.info("Stock restaurado para producto: {} cantidad: {}",
                        previousSaleProduct.getProduct().getName(),
                        previousSaleProduct.getQuantity());
            }
        });
    }

    private void adjustStockForUpdatedProducts(Sale sale, List<SaleProductRequestDto> saleProductRequests, List<Product> products) {
        saleProductRequests.forEach(saleProductRequest -> {
            Product product = findProductByCodeInList(products, saleProductRequest.productCode());

            Double previousQuantity = sale.getSaleProducts().stream()
                    .filter(sp -> sp.getProduct().getProductCode().equals(saleProductRequest.productCode()))
                    .map(SaleProduct::getQuantity)
                    .findFirst()
                    .orElse(0.0);

            Double difference = saleProductRequest.quantity() - previousQuantity;

            if (difference > 0) {
                validateSufficientStock(product, difference);
            }

            adjustStock(product.getProductCode(), difference);
        });
    }

    private void adjustStock(Long productCode, Double difference) {
        if (difference > 0) {
            productRepository.decreaseStock(productCode, difference);
        } else if (difference < 0) {
            productRepository.increaseStock(productCode, Math.abs(difference));
        }
    }
}
