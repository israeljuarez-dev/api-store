package com.app.store_api.domain;

import com.app.store_api.persistence.listener.SaleEntityListener;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"products"})
@ToString(exclude = {"products"})
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(SaleEntityListener.class)
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name ="total_amount", nullable = false)
    BigDecimal totalAmount;

    @ManyToMany(
            targetEntity = Product.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE
    )
    @JoinTable(
            name = "sale_products",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @OrderBy("price ASC")
    List<Product> products;

    @ManyToOne(
            targetEntity = Customer.class,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;
}
