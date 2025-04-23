package com.sandipsky.spring_boot_inventory_system.repository;

import com.sandipsky.spring_boot_inventory_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    
}

