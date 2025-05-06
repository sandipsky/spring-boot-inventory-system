package com.sandipsky.inventory_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sandipsky.inventory_system.dto.ApiResponse;
import com.sandipsky.inventory_system.dto.PartyDTO;
import com.sandipsky.inventory_system.dto.filter.RequestDTO;
import com.sandipsky.inventory_system.entity.Party;
import com.sandipsky.inventory_system.service.PartyService;
import com.sandipsky.inventory_system.util.ResponseUtil;

import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/party")
public class PartyController {

    @Autowired
    private PartyService service;

    @GetMapping()
    public List<PartyDTO> getPartys() {
        return service.getPartys();
    }

    @PostMapping("/view")
    public Page<PartyDTO> getPaginatedPartysList(@RequestBody RequestDTO request) {
        return service.getPaginatedPartysList(request);
    }

    @GetMapping("/{id}")
    public PartyDTO getParty(@PathVariable int id) {
        return service.getPartyById(id);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Party>> createParty(@RequestBody PartyDTO party) {
        Party res = service.saveParty(party);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "Party Added successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Party>> updateParty(@PathVariable int id, @RequestBody PartyDTO party) {
        Party res = service.updateParty(id, party);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "Party Updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Party>> deleteParty(@PathVariable int id) {
        service.deleteParty(id);
        return ResponseEntity.ok(ResponseUtil.success(id, "Party Deleted successfully"));
    }
}
