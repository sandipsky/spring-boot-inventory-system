package com.sandipsky.inventory_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sandipsky.inventory_system.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);
    boolean existsByUsernameAndIdNot(String username, int id);
}

