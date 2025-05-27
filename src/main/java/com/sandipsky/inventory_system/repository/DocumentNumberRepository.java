package com.sandipsky.inventory_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sandipsky.inventory_system.entity.DocumentNumber;

public interface DocumentNumberRepository
        extends JpaRepository<DocumentNumber, Integer>, JpaSpecificationExecutor<DocumentNumber> {

    boolean existsByModule(String module);

    boolean existsByModuleAndIdNot(String module, int id);

    Optional<DocumentNumber> findByModule(String module);
}
