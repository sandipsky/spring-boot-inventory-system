package com.sandipsky.inventory_system.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sandipsky.inventory_system.entity.MasterPurchaseEntry;


public interface MasterPurchaseEntryRepository extends JpaRepository<MasterPurchaseEntry, Integer>, JpaSpecificationExecutor<MasterPurchaseEntry> {
    Optional<MasterPurchaseEntry> findTopByOrderByIdDesc();
}
