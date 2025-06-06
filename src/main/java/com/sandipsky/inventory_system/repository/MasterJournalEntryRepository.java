package com.sandipsky.inventory_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sandipsky.inventory_system.entity.MasterJournalEntry;

public interface MasterJournalEntryRepository
        extends JpaRepository<MasterJournalEntry, Integer>, JpaSpecificationExecutor<MasterJournalEntry> {
    Optional<MasterJournalEntry> findTopByOrderByIdDesc();

    @Query("""
                SELECT j
                FROM MasterJournalEntry j
                WHERE j.masterPurchaseEntry.id = :masterPurchaseEntryId
            """)
    Optional<MasterJournalEntry> findByMasterPurchaseEntryId(@Param("masterPurchaseEntryId") Integer masterPurchaseEntryId);
}
