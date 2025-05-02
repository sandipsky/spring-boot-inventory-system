package com.sandipsky.inventory_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sandipsky.inventory_system.dto.DropdownDTO;
import com.sandipsky.inventory_system.service.DropdownService;

import java.util.List;

@RestController
@RequestMapping("/dropdown")
public class DropdownController {

    @Autowired
    private DropdownService service;

    @GetMapping("/products/{serviceType}/{type}/{status}")
    public List<DropdownDTO> getProductsDropdown(
            @PathVariable String serviceType,
            @PathVariable String type,
            @PathVariable String status) {
        return service.getProductsDropdown(serviceType, type, status);
    }

    @GetMapping("/units/{status}")
    public List<DropdownDTO> getUnitsDropdown(@PathVariable String status) {
        return service.getUnitsDropdown(status);
    }

    @GetMapping("/categorys/{status}")
    public List<DropdownDTO> getCategorysDropdown(@PathVariable String status) {
        return service.getCategoryDropdown(status);
    }

    @GetMapping("/users/{status}")
    public List<DropdownDTO> getUsersDropdown(@PathVariable String status) {
        return service.getUserDropdown(status);
    }
}
