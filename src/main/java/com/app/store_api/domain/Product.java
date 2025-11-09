package com.app.store_api.domain;

import com.app.store_api.persistence.listener.ProductEntityListener;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(ProductEntityListener.class)
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false, length = 50, unique = true)
    String name;

    @Column(name = "trade_mark", nullable = false, length = 50)
    String tradeMark;

    @Column(nullable = false)
    BigDecimal price;

    @Column(nullable = false, length = 500)
    String description;

    @Column(nullable = false)
    Integer stock;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;
}
