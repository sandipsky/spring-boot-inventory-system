package com.sandipsky.inventory_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sandipsky.inventory_system.dto.ApiResponse;
import com.sandipsky.inventory_system.entity.DocumentNumber;
import com.sandipsky.inventory_system.service.DocumentNumberService;
import com.sandipsky.inventory_system.util.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("/documentNumber")
public class DocumentNumberController {

    @Autowired
    private DocumentNumberService service;

    @GetMapping()
    public List<DocumentNumber> getDocumentNumbers() {
        return service.getDocumentNumbers();
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<DocumentNumber>> createDocumentNumber(
            @RequestBody DocumentNumber documentNumber) {
        DocumentNumber res = service.saveDocumentNumber(documentNumber);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "DocumentNumber Added successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DocumentNumber>> updateDocumentNumber(@PathVariable int id,
            @RequestBody DocumentNumber documentNumber) {
        DocumentNumber res = service.updateDocumentNumber(id, documentNumber);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "DocumentNumber Updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DocumentNumber>> deleteDocumentNumber(@PathVariable int id) {
        service.deleteDocumentNumber(id);
        return ResponseEntity.ok(ResponseUtil.success(id, "DocumentNumber Deleted successfully"));
    }

    @GetMapping("/generatePurchaseNumber")
    public String generatePurchaseNumber() {
        return service.generatePurchaseNumber();
    }
}
