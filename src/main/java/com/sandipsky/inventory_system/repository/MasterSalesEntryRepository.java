package com.sandipsky.inventory_system.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sandipsky.inventory_system.entity.MasterSalesEntry;


public interface MasterSalesEntryRepository extends JpaRepository<MasterSalesEntry, Integer>, JpaSpecificationExecutor<MasterSalesEntry> {
    Optional<MasterSalesEntry> findTopByOrderByIdDesc();
}
