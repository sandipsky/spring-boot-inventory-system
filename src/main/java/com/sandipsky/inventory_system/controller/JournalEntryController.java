package com.sandipsky.inventory_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sandipsky.inventory_system.dto.ApiResponse;
import com.sandipsky.inventory_system.dto.journal.MasterJournalEntryDTO;
import com.sandipsky.inventory_system.entity.MasterJournalEntry;
import com.sandipsky.inventory_system.service.JournalEntryService;
import com.sandipsky.inventory_system.util.ResponseUtil;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService service;

    @GetMapping()
    public List<MasterJournalEntryDTO> getPaginatedMasterPurchaseEntrysList() {
        return service.getAllJournalReport();
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<MasterJournalEntry>> createMasterJournalEntry(
            @RequestBody MasterJournalEntryDTO masterJournalEntryDTO) {
        MasterJournalEntry res = service.saveMasterJournalEntry(masterJournalEntryDTO);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "Journal Added successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MasterJournalEntry>> updateMasterJournalEntry(@PathVariable int id,
            @RequestBody MasterJournalEntryDTO masterJournalEntryDTO) {
        MasterJournalEntry res = service.updateMasterJournalEntry(id, masterJournalEntryDTO);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "Journal Updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<MasterJournalEntry>> deleteMasterJournalEntry(@PathVariable int id) {
        service.deleteMasterJournalEntry(id);
        return ResponseEntity.ok(ResponseUtil.success(id, "Journal Deleted successfully"));
    }
}
