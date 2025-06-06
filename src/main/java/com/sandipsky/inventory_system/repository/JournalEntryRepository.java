package com.sandipsky.inventory_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sandipsky.inventory_system.entity.JournalEntry;


public interface JournalEntryRepository extends JpaRepository<JournalEntry, Integer>, JpaSpecificationExecutor<JournalEntry> {
    List<JournalEntry> findByMasterJournalEntryId(Integer masterJournalEntryId);
}
