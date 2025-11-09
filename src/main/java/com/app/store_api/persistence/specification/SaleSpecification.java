package com.app.store_api.persistence.specification;

import com.app.store_api.domain.Customer;
import com.app.store_api.domain.Product;
import com.app.store_api.domain.Sale;
import com.app.store_api.dto.criteria.SearchSaleCriteriaDTO;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SaleSpecification {

    public static Specification<Sale> withSearchCriteria (SearchSaleCriteriaDTO criteriaDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteriaDTO.getId() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("saleId"), criteriaDTO.getId())
                );
            }

            if (criteriaDTO.getCreationDate() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("creationDate"), criteriaDTO.getCreationDate())
                );
            }

            if (criteriaDTO.getCustomerId() != null) {
                Join<Sale, Customer> customerJoin = root.join("customer", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(customerJoin.get("customerId"), criteriaDTO.getCustomerId()));
            }

            if (criteriaDTO.getCustomerName() != null) {
                Join<Sale, Customer> customerJoin = root.join("customer", JoinType.INNER);
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(customerJoin.get("name")),
                                "%" + criteriaDTO.getCustomerName().toLowerCase() + "%"
                        )
                );
            }

            if (criteriaDTO.getProductIds() != null && !criteriaDTO.getProductIds().isEmpty()) {
                Join<Sale, Product> productJoin = root.join("products", JoinType.INNER);
                predicates.add(productJoin.get("productId").in(criteriaDTO.getProductIds()));
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
