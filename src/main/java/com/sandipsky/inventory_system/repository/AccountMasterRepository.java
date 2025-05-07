package com.sandipsky.inventory_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sandipsky.inventory_system.entity.AccountMaster;
import com.sandipsky.inventory_system.dto.DropdownDTO;

public interface AccountMasterRepository
    extends JpaRepository<AccountMaster, Integer>, JpaSpecificationExecutor<AccountMaster> {
  boolean existsByAccountCode(String accountCode);

  boolean existsByAccountCodeAndIdNot(String accountCode, int id);

  AccountMaster findByAccountName(String accountName);

  // @Query("""
  // SELECT new com.sandipsky.inventory_system.dto.DropdownDTO(p.id, p.name)
  // FROM AccountMaster p
  // WHERE (:type IS NULL OR p.type = :type)
  // AND (:isActive IS NULL OR p.isActive = :isActive)
  // """)
  // List<DropdownDTO> findFilteredDropdown(String type,
  // Boolean isActive);

  @Query("""
          SELECT p
          FROM AccountMaster p
          WHERE p.party.id = :partyId
      """)
  AccountMaster findByPartyId(@Param("partyId") Integer partyId);
}
