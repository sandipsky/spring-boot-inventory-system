package com.sandipsky.inventory_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sandipsky.inventory_system.entity.SalesEntry;


public interface SalesEntryRepository extends JpaRepository<SalesEntry, Integer>, JpaSpecificationExecutor<SalesEntry> {
    List<SalesEntry> findByMasterSalesEntryId(Integer masterSalesEntryId);
}
