package com.sandipsky.inventory_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.inventory_system.entity.Unit;
import com.sandipsky.inventory_system.exception.DuplicateResourceException;
import com.sandipsky.inventory_system.exception.ResourceNotFoundException;
import com.sandipsky.inventory_system.repository.UnitRepository;

import java.util.List;

@Service
public class UnitService {

    @Autowired
    private UnitRepository repository;

    public Unit saveUnit(Unit unit) {
        if (unit.getName() == null || unit.getName().trim().isEmpty()) {
            throw new RuntimeException("Unit name cannot be null or blank");
        }
        if (repository.existsByName(unit.getName().trim())) {
            throw new DuplicateResourceException("Unit with the same name already exists");
        }
        unit.setName(unit.getName().trim());
        return repository.save(unit);
    }

    public List<Unit> getUnits() {
        return repository.findAll();
    }

    public Unit getUnitById(int id) {
        Unit existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Unit not found"));
        return existing;
    }

    public Unit updateUnit(int id, Unit unit) {
        if (unit.getName() == null || unit.getName().trim().isEmpty()) {
            throw new RuntimeException("Unit name cannot be null or blank");
        }
        Unit existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Unit not found"));

        if (repository.existsByNameAndIdNot(unit.getName().trim(), id)) {
            throw new DuplicateResourceException("Unit with the same name already exists");
        }
        existing.setName(unit.getName().trim());
        existing.setActive(unit.isActive());
        return repository.save(existing);
    }

    public void deleteUnit(int id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Unit not found"));
        repository.deleteById(id);
    }
}
