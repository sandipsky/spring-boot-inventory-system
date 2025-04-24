package com.sandipsky.inventory_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sandipsky.inventory_system.entity.Unit;
import com.sandipsky.inventory_system.service.UnitService;

import java.util.List;

@RestController
@RequestMapping("/units")
public class UnitController {

    @Autowired
    private UnitService service;

    @GetMapping()
    public List<Unit> getUnits() {
        return service.getUnits();
    }

    @GetMapping("/{id}")
    public Unit getUnit(@PathVariable int id) {
        return service.getUnitById(id);
    }

    @PostMapping()
    public Unit createUnit(@RequestBody Unit unit) {
        return service.saveUnit(unit);
    }

    @PutMapping("/{id}")
    public Unit updateUnit(@PathVariable int id, @RequestBody Unit unit) {
        return service.updateUnit(id, unit);
    }

    @DeleteMapping("/{id}")
    public void deleteUnit(@PathVariable int id) {
        service.deleteUnit(id);
    }
}
