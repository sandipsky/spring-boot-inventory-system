package com.sandipsky.inventory_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sandipsky.inventory_system.entity.Party;
import com.sandipsky.inventory_system.dto.DropdownDTO;

public interface PartyRepository extends JpaRepository<Party, Integer>, JpaSpecificationExecutor<Party> {
  boolean existsByName(String name);

  boolean existsByNameAndIdNot(String name, int id);

  @Query("""
          SELECT new com.sandipsky.inventory_system.dto.DropdownDTO(p.id, p.name)
          FROM Party p
          WHERE (:type IS NULL OR p.type = :type)
            AND (:isActive IS NULL OR p.isActive = :isActive)
      """)
  List<DropdownDTO> findFilteredDropdown(String type,
      Boolean isActive);
}
