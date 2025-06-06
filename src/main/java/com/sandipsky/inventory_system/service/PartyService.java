package com.sandipsky.inventory_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.inventory_system.dto.PartyDTO;
import com.sandipsky.inventory_system.dto.filter.RequestDTO;
import com.sandipsky.inventory_system.entity.AccountMaster;
import com.sandipsky.inventory_system.entity.Party;
import com.sandipsky.inventory_system.exception.ResourceNotFoundException;
import com.sandipsky.inventory_system.repository.AccountMasterRepository;
import com.sandipsky.inventory_system.repository.PartyRepository;
import com.sandipsky.inventory_system.util.SpecificationBuilder;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Service
public class PartyService {

    @Autowired
    private PartyRepository repository;

    @Autowired
    private AccountMasterRepository accountMasterRepository;

    private final SpecificationBuilder<Party> specBuilder = new SpecificationBuilder<>();

    @Transactional
    public Party saveParty(PartyDTO dto) {

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new RuntimeException("Party name cannot be null or blank");
        }

        Party party = new Party();
        mapDtoToEntity(dto, party);

        AccountMaster pAccountMaster = new AccountMaster();
        pAccountMaster.setAccountName(party.getName());
        pAccountMaster.setParty(party);
        if (party.getType().equals("Customer")) {
            pAccountMaster.setAccountType("Receivables");
            AccountMaster parent = accountMasterRepository.findByAccountName("Trade Receivables")
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
            pAccountMaster.setParentId(parent.getId());
            pAccountMaster.setParentAccountName(parent.getAccountName());
            pAccountMaster.setPartyType("Customer");
            pAccountMaster.setRemarks("For sales entry purpose");
        } else {
            pAccountMaster.setAccountType("Payables");
            AccountMaster parent = accountMasterRepository.findByAccountName("Trader Payable")
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
            pAccountMaster.setParentId(parent.getId());
            pAccountMaster.setParentAccountName(parent.getAccountName());
            pAccountMaster.setPartyType("Vendor");
            pAccountMaster.setRemarks("For purchase entry purpose");
        }
        accountMasterRepository.save(pAccountMaster);

        return repository.save(party);
    }

    public Page<PartyDTO> getPaginatedPartysList(RequestDTO request) {
        Pageable pageable = PageRequest.of(
                request.getPagination() != null ? request.getPagination().getPageIndex() : 0,
                request.getPagination() != null ? request.getPagination().getPageSize() : 25,
                specBuilder.buildSort(request.getSortDTO()));

        Specification<Party> spec = specBuilder.buildSpecification(request.getFilter());
        Page<Party> partyPage = repository.findAll(spec, pageable);
        return partyPage.map(this::mapToDTO);
    }

    public List<PartyDTO> getPartys() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public PartyDTO getPartyById(int id) {
        Party party = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Party not found"));
        return mapToDTO(party);
    }

    public Party updateParty(int id, PartyDTO party) {
        Party existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Party not found"));

        if (party.getName() == null || party.getName().trim().isEmpty()) {
            throw new RuntimeException("Party name cannot be null or blank");
        }

        mapDtoToEntity(party, existing);

        AccountMaster pAccountMaster = accountMasterRepository.findByPartyId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (existing.getType().equals("Customer")) {
            pAccountMaster.setAccountType("Receivables");
            AccountMaster parent = accountMasterRepository.findByAccountName("Trade Receivables")
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
            pAccountMaster.setParentId(parent.getId());
            pAccountMaster.setParentAccountName(parent.getAccountName());
            pAccountMaster.setPartyType("Customer");
            pAccountMaster.setRemarks("For sales entry purpose");
        } else {
            pAccountMaster.setAccountType("Payables");
            AccountMaster parent = accountMasterRepository.findByAccountName("Trader Payable")
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
            pAccountMaster.setParentId(parent.getId());
            pAccountMaster.setParentAccountName(parent.getAccountName());
            pAccountMaster.setPartyType("Vendor");
            pAccountMaster.setRemarks("For purchase entry purpose");
        }
        accountMasterRepository.save(pAccountMaster);
        return repository.save(existing);
    }

    public void deleteParty(int id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Party not found"));
        repository.deleteById(id);
    }

    private PartyDTO mapToDTO(Party party) {
        PartyDTO dto = new PartyDTO();
        dto.setId(party.getId());
        dto.setName(party.getName());
        dto.setRegistrationNumber(party.getRegistrationNumber());
        dto.setContact(party.getContact());
        dto.setEmail(party.getEmail());
        dto.setAddress(party.getAddress());
        dto.setRemarks(party.getRemarks());
        dto.setType(party.getType());
        dto.setActive(party.isActive());
        return dto;
    }

    private void mapDtoToEntity(PartyDTO dto, Party party) {
        party.setName(dto.getName().trim());
        party.setRegistrationNumber(dto.getRegistrationNumber().trim());
        party.setContact(dto.getContact().trim());
        party.setEmail(dto.getEmail().trim());
        party.setAddress(dto.getAddress().trim());
        party.setRemarks(dto.getRemarks().trim());
        party.setType(dto.getType().trim());
        party.setActive(dto.isActive());
    }
}
