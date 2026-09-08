package com.israel.api_store.sale.model;

import com.israel.api_store.customer.model.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "saleProducts")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long saleCode;

    @Column(nullable = false)
    private LocalDate saleDate;

    @Column(nullable = false)
    private BigDecimal total;

    @OneToMany(
            mappedBy = "sale",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SaleProduct> saleProducts;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}