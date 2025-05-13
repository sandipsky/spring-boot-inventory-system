package com.sandipsky.inventory_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sandipsky.inventory_system.entity.Product;

public interface PurchaseEntryRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
}
