package com.israel.api_store.sale.model;

import com.israel.api_store.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "sale")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "sale_products")
public class SaleProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    // sale_code
    @ManyToOne
    @JoinColumn(name = "sale_code", nullable = false)
    private Sale sale;

    // product code
    @ManyToOne
    @JoinColumn(name = "product_code", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Double quantity;
}
