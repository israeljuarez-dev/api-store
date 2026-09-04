package com.israel.api_store.product.repository;

import com.israel.api_store.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.availableQuantity <= 5")
    List<Product> findProductsWithLowStock();

    @Query("UPDATE Product p SET p.availableQuantity = p.availableQuantity - :quantity WHERE p.productCode = :productCode")
    @Modifying
    int decreaseStock(@Param("productCode") Long productCode, @Param("quantity") Double quantity);

    @Query("UPDATE Product p SET p.availableQuantity = p.availableQuantity + :quantity WHERE p.productCode = :productCode")
    @Modifying
    int increaseStock(@Param("productCode") Long productCode, @Param("quantity") Double quantity);
}
