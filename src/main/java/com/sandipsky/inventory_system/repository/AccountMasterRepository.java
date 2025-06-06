package com.sandipsky.inventory_system.repository;

import java.util.List;
import java.util.Optional;

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

    Optional<AccountMaster> findByAccountName(String accountName);

    @Query("""
            SELECT new com.sandipsky.inventory_system.dto.DropdownDTO(p.id, p.accountName)
            FROM AccountMaster p
            WHERE (:type IS NULL OR
                   (:type = TRUE AND p.party.id IS NOT NULL) OR
                   (:type = FALSE AND p.party.id IS NULL))
              AND (:isActive IS NULL OR p.isActive = :isActive)
              AND (
                  p.party.id IS NULL OR
                  (:partyType IS NULL OR p.partyType = :partyType)
              )
            """)
    List<DropdownDTO> findFilteredDropdown(Boolean type,
            String partyType, Boolean isActive);

    @Query("""
                SELECT p
                FROM AccountMaster p
                WHERE p.party.id = :partyId
            """)
    Optional<AccountMaster> findByPartyId(@Param("partyId") Integer partyId);
}
