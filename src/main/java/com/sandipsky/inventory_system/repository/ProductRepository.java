package com.sandipsky.inventory_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sandipsky.inventory_system.entity.Product;
import com.sandipsky.inventory_system.dto.DropdownDTO;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, int id);

    @Query("""
                SELECT new com.sandipsky.inventory_system.dto.DropdownDTO(p.id, p.name)
                FROM Product p
                WHERE (:isService IS NULL OR p.isServiceItem = :isService)
                  AND (:isPurchasable IS NULL OR p.isPurchasable = :isPurchasable)
                  AND (:isSellable IS NULL OR p.isSellable = :isSellable)
                  AND (:isActive IS NULL OR p.isActive = :isActive)
            """)
    List<DropdownDTO> findFilteredDropdown(Boolean isService, Boolean isPurchasable, Boolean isSellable,
            Boolean isActive);
}
