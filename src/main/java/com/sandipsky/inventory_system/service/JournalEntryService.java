package com.sandipsky.inventory_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sandipsky.inventory_system.dto.journal.MasterJournalEntryDTO;
import com.sandipsky.inventory_system.dto.journal.JournalEntryDTO;
import com.sandipsky.inventory_system.dto.filter.RequestDTO;
import com.sandipsky.inventory_system.entity.MasterJournalEntry;
import com.sandipsky.inventory_system.entity.AccountMaster;
import com.sandipsky.inventory_system.entity.JournalEntry;
import com.sandipsky.inventory_system.exception.ResourceNotFoundException;
import com.sandipsky.inventory_system.repository.MasterJournalEntryRepository;
import com.sandipsky.inventory_system.repository.AccountMasterRepository;
import com.sandipsky.inventory_system.repository.JournalEntryRepository;
import com.sandipsky.inventory_system.util.SpecificationBuilder;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

@Service
public class JournalEntryService {
    @Autowired
    private MasterJournalEntryRepository repository;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private AccountMasterRepository accountMasterRepository;

    @Autowired
    private DocumentNumberService documentNumberService;

    private final SpecificationBuilder<MasterJournalEntry> specBuilder = new SpecificationBuilder<>();

    public Page<MasterJournalEntryDTO> getPaginatedMasterJournalEntrysList(RequestDTO request) {
        Pageable pageable = PageRequest.of(
                request.getPagination() != null ? request.getPagination().getPageIndex() : 0,
                request.getPagination() != null ? request.getPagination().getPageSize() : 25,
                specBuilder.buildSort(request.getSortDTO()));

        Specification<MasterJournalEntry> spec = specBuilder.buildSpecification(request.getFilter());
        Page<MasterJournalEntry> productPage = repository.findAll(spec, pageable);
        return productPage.map(this::mapToDTO);
    }

    public MasterJournalEntryDTO getMasterJournalEntryById(int id) {
        MasterJournalEntry masterJournalEntry = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journal Entry with Given Id not found"));
        MasterJournalEntryDTO masterJournalEntryDTO = new MasterJournalEntryDTO();
        masterJournalEntryDTO.setId(masterJournalEntry.getId());
        masterJournalEntryDTO.setDate(masterJournalEntry.getDate());
        masterJournalEntryDTO.setSystemEntryNo(masterJournalEntry.getSystemEntryNo());
        masterJournalEntryDTO.setRemarks(masterJournalEntry.getRemarks());

        if (masterJournalEntry.getJournalEntries() != null) {
            masterJournalEntryDTO.setJournalEntries(
                    masterJournalEntry.getJournalEntries().stream()
                            .map(journalEntry -> {
                                JournalEntryDTO journalEntryDTO = new JournalEntryDTO();
                                journalEntryDTO.setId(journalEntry.getId());
                                journalEntryDTO.setAccountMasterId(journalEntry.getMasterAccount().getId());
                                journalEntryDTO.setAccountMasterName(journalEntry.getMasterAccount().getAccountName());
                                journalEntryDTO.setCreditAmount(journalEntry.getCreditAmount());
                                journalEntryDTO.setDebitAmount(journalEntry.getDebitAmount());
                                journalEntryDTO.setNarration(journalEntry.getNarration());
                                journalEntryDTO.setMasterJournalEntryId(journalEntry.getMasterJournalEntryId());
                                return journalEntryDTO;
                            }).toList());
        }
        return masterJournalEntryDTO;
    }

    @Transactional
    public MasterJournalEntry saveMasterJournalEntry(MasterJournalEntryDTO masterJournalEntryDTO) {
        MasterJournalEntry masterJournalEntry = new MasterJournalEntry();
        masterJournalEntry.setDate(masterJournalEntryDTO.getDate());
        masterJournalEntry.setRemarks(masterJournalEntryDTO.getRemarks());
        masterJournalEntry.setSystemEntryNo(documentNumberService.generateJournalNumber());

        MasterJournalEntry savedEntry = repository.save(masterJournalEntry);

        if (masterJournalEntryDTO.getJournalEntries() != null) {
            for (JournalEntryDTO item : masterJournalEntryDTO.getJournalEntries()) {
                // Saving journal Entry
                JournalEntry journalEntry = new JournalEntry();
                AccountMaster accountMaster = accountMasterRepository.findById(item.getAccountMasterId())
                        .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
                journalEntry.setMasterAccount(accountMaster);
                journalEntry.setCreditAmount(item.getCreditAmount());
                journalEntry.setDebitAmount(item.getDebitAmount());
                journalEntry.setNarration(item.getNarration());
                journalEntry.setMasterJournalEntryId(savedEntry.getId());
                journalEntryRepository.save(journalEntry);
            }
        }
        return savedEntry;
    }

    @Transactional
    public MasterJournalEntry updateMasterJournalEntry(int id, MasterJournalEntryDTO masterJournalEntryDTO) {
        MasterJournalEntry masterJournalEntry = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journal Entry with Given Id not found"));
        masterJournalEntry.setDate(masterJournalEntryDTO.getDate());
        masterJournalEntry.setRemarks(masterJournalEntryDTO.getRemarks());

        List<JournalEntry> existingEntries = journalEntryRepository
                .findByMasterJournalEntryId(masterJournalEntry.getId());
        for (JournalEntry existing : existingEntries) {
            journalEntryRepository.delete(existing);
        }

        if (masterJournalEntryDTO.getJournalEntries() != null) {
            for (JournalEntryDTO item : masterJournalEntryDTO.getJournalEntries()) {
                // Saving journal Entry
                JournalEntry journalEntry = new JournalEntry();
                AccountMaster accountMaster = accountMasterRepository.findById(item.getAccountMasterId())
                        .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
                journalEntry.setMasterAccount(accountMaster);
                journalEntry.setCreditAmount(item.getCreditAmount());
                journalEntry.setDebitAmount(item.getDebitAmount());
                journalEntry.setNarration(item.getNarration());
                journalEntry.setMasterJournalEntryId(masterJournalEntry.getId());
                journalEntryRepository.save(journalEntry);
            }
        }

        return masterJournalEntry;
    }

    @Transactional
    public void deleteMasterJournalEntry(int id) {
        MasterJournalEntry masterJournalEntry = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journal Entry with Given Id not found"));
        for (JournalEntry item : masterJournalEntry.getJournalEntries()) {
            journalEntryRepository.deleteById(item.getId());
        }
        repository.deleteById(id);
    }

    private MasterJournalEntryDTO mapToDTO(MasterJournalEntry entity) {
        MasterJournalEntryDTO masterJournalEntryDTO = new MasterJournalEntryDTO();
        masterJournalEntryDTO.setId(entity.getId());
        masterJournalEntryDTO.setDate(entity.getDate());
        masterJournalEntryDTO.setSystemEntryNo(entity.getSystemEntryNo());
        masterJournalEntryDTO.setRemarks(entity.getRemarks());
        return masterJournalEntryDTO;
    }

}
