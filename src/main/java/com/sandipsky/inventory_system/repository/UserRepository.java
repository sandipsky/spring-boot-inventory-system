package com.sandipsky.inventory_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sandipsky.inventory_system.dto.DropdownDTO;
import com.sandipsky.inventory_system.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsernameAndIdNot(String username, int id);

    boolean existsByEmailAndIdNot(String email, int id);

    @Query("""
                SELECT new com.sandipsky.inventory_system.dto.DropdownDTO(u.id, u.username)
                FROM User u
                WHERE (:isActive IS NULL OR u.isActive = :isActive)
            """)
    List<DropdownDTO> findFilteredDropdown(
            Boolean isActive);
}
