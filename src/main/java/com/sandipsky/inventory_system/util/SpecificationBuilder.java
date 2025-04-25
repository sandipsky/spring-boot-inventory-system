package com.sandipsky.inventory_system.util;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.sandipsky.inventory_system.dto.filter.FilterDTO;
import com.sandipsky.inventory_system.dto.filter.SortDTO;

import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {

    public Specification<T> buildSpecification(List<FilterDTO> filters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filters != null) {
                for (FilterDTO filter : filters) {
                    if (filter.getField() != null && filter.getValue() != null) {
                        try {
                            predicates.add(cb.like(cb.lower(root.get(filter.getField()).as(String.class)),
                                                   "%" + filter.getValue().toLowerCase() + "%"));
                        } catch (IllegalArgumentException e) {
                            // Invalid field — skip
                            System.out.println("Skipping unknown filter field: " + filter.getField());
                        }
                    }
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Sort buildSort(List<SortDTO> sortDTOs) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortDTOs != null) {
            for (SortDTO dto : sortDTOs) {
                if (dto.getField() != null && dto.getOrderType() != null) {
                    Sort.Order order = "desc".equalsIgnoreCase(dto.getOrderType())
                        ? Sort.Order.desc(dto.getField())
                        : Sort.Order.asc(dto.getField());
                    orders.add(order);
                }
            }
        }
        return orders.isEmpty() ? Sort.unsorted() : Sort.by(orders);
    }
}
