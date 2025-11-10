package com.app.store_api.persistence.specification;

import com.app.store_api.domain.Product;
import com.app.store_api.dto.criteria.SearchProductCriteriaDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> withSearchCriteria(SearchProductCriteriaDTO criteriaDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteriaDTO.getId() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("productId"), criteriaDTO.getId())
                );
            }

            if(criteriaDTO.getName() != null){
                predicates.add(
                        criteriaBuilder.equal(root.get("name"), criteriaDTO.getName())
                );
            }

            if (criteriaDTO.getTradeMark() != null){
                predicates.add(
                        criteriaBuilder.equal(root.get("tradeMark"), criteriaDTO.getTradeMark())
                );
            }

            if (criteriaDTO.getPrice() != null){
                predicates.add(
                        criteriaBuilder.equal(root.get("price"), criteriaDTO.getPrice())
                );
            }

            if(criteriaDTO.getStock() != null){
                predicates.add(criteriaBuilder.equal(root.get("stock"), criteriaDTO.getStock()));
            }

            if (criteriaDTO.getCreationDate() != null){
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
