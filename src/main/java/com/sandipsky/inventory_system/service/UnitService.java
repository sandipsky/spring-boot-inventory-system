package com.sandipsky.inventory_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.inventory_system.entity.Unit;
import com.sandipsky.inventory_system.repository.UnitRepository;

import java.util.List;

@Service
public class UnitService {

    @Autowired
    private UnitRepository repository;

    public Unit saveUnit(Unit unit) {
        return repository.save(unit);
    }

    public List<Unit> getUnits() {
        return repository.findAll();
    }

    public Unit getUnitById(int id) {
        return repository.findById(id).get();
    }

    public Unit updateUnit(int id, Unit unit) {
        Unit existing = repository.findById(id).get();
        existing.setName(unit.getName());
        existing.setActive(unit.isActive());
        return repository.save(existing);
    }

    public void deleteUnit(int id) {
        repository.deleteById(id);
    }
}
