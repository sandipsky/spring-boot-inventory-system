package com.sandipsky.spring_boot_inventory_system.repository;

import com.sandipsky.spring_boot_inventory_system.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Integer> {
    
}

