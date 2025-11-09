package com.app.store_api.domain;

import com.app.store_api.persistence.listener.CustomerEntityListener;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
@EntityListeners(CustomerEntityListener.class)
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false, length = 50)
    String name;

    @Column(name = "last_name",nullable = false, length = 80)
    String lastName;

    @Column(unique = true, nullable = false, length = 12)
    String dni;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;
}
