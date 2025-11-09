package com.app.store_api.dto.criteria;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchCustomerCriteriaDTO {

    UUID id;

    String name;

    String lastName;

    String dni;

    LocalDate creationDate;

    String sortField; //campo a ordenar

    String sortingDirection; //direccion de ordenamiento, ascendente o descendente

    Integer pageActual = 0; //representa la página en la que se encuentra

    Integer pageSize = 10; //representa la cantidad de registros se mostrarán por página

    // paginación ejemplo: tengo 12 registros y con pageSize pédimos 4 registros por página, tendríamos 3 páginas
    // luego podemos: ir a la página 2 con pageActual y pedir los primeros 2 registros de ahí con pageSize
}
