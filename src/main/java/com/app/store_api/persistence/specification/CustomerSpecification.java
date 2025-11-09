package com.app.store_api.persistence.specification;

import com.app.store_api.domain.Customer;
import com.app.store_api.dto.criteria.SearchCustomerCriteriaDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification {

    public static Specification<Customer> withSearchCriteria(SearchCustomerCriteriaDTO criteriaDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (criteriaDTO.getId() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("customerId"), criteriaDTO.getId())
                );
            }

            if(criteriaDTO.getName() != null){
                predicates.add(
                        criteriaBuilder.equal(root.get("name"), criteriaDTO.getName())
                );
            }

            if(criteriaDTO.getLastName() != null){
                predicates.add(
                        criteriaBuilder.equal(root.get("lastName"), criteriaDTO.getLastName())
                );
            }

            if (criteriaDTO.getDni() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("dni"), criteriaDTO.getDni())
                );
            }

            if (criteriaDTO.getCreationDate() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("creationDate"), criteriaDTO.getCreationDate())
                );
            }

            if (criteriaDTO.getSortingDirection() != null && criteriaDTO.getSortField() != null) {
                assert query != null;
                if (criteriaDTO.getSortingDirection().equalsIgnoreCase("desc")) {
                    query.orderBy(
                            criteriaBuilder.desc(root.get(criteriaDTO.getSortField()))
                    );
                } else {
                    query.orderBy(
                            criteriaBuilder.asc(root.get(criteriaDTO.getSortField()))
                    );
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
