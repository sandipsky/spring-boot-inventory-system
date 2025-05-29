package com.sandipsky.inventory_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sandipsky.inventory_system.dto.ApiResponse;
import com.sandipsky.inventory_system.dto.sales.MasterSalesEntryDTO;
import com.sandipsky.inventory_system.dto.filter.RequestDTO;
import com.sandipsky.inventory_system.entity.MasterSalesEntry;
import com.sandipsky.inventory_system.service.MasterSalesEntryService;
import com.sandipsky.inventory_system.util.ResponseUtil;

import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/sales")
public class MasterSalesEntryController {

    @Autowired
    private MasterSalesEntryService service;

    @PostMapping("/view")
    public Page<MasterSalesEntryDTO> getPaginatedMasterSalesEntrysList(@RequestBody RequestDTO request) {
        return service.getPaginatedMasterSalesEntrysList(request);
    }

    @GetMapping("/{id}")
    public MasterSalesEntryDTO getMasterSalesEntry(@PathVariable int id) {
        return service.getMasterSalesEntryById(id);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<MasterSalesEntry>> createMasterSalesEntry(@RequestBody MasterSalesEntryDTO masterSalesEntryDTO) {
        MasterSalesEntry res = service.saveMasterSalesEntry(masterSalesEntryDTO);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "Sales Added successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<MasterSalesEntry>> deleteMasterSalesEntry(@PathVariable int id) {
        service.deleteMasterSalesEntry(id);
        return ResponseEntity.ok(ResponseUtil.success(id, "Sales Deleted successfully"));
    }
}
