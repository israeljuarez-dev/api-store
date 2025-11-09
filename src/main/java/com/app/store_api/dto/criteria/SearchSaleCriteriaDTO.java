package com.app.store_api.dto.criteria;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchSaleCriteriaDTO {
    UUID id;

    LocalDate creationDate;

    UUID customerId;

    String customerName;

    List<UUID> productIds;

    String sortField;

    String sortingDirection;

    Integer pageActual = 0;

    Integer pageSize = 10;
}