package com.sandipsky.inventory_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sandipsky.inventory_system.entity.PurchaseEntry;


public interface PurchaseEntryRepository extends JpaRepository<PurchaseEntry, Integer>, JpaSpecificationExecutor<PurchaseEntry> {
}
