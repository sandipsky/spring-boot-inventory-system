package com.sandipsky.inventory_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.inventory_system.entity.DocumentNumber;
import com.sandipsky.inventory_system.entity.MasterPurchaseEntry;
import com.sandipsky.inventory_system.entity.MasterSalesEntry;
import com.sandipsky.inventory_system.exception.DuplicateResourceException;
import com.sandipsky.inventory_system.exception.ResourceNotFoundException;
import com.sandipsky.inventory_system.repository.DocumentNumberRepository;
import com.sandipsky.inventory_system.repository.MasterPurchaseEntryRepository;
import com.sandipsky.inventory_system.repository.MasterSalesEntryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentNumberService {

    @Autowired
    private DocumentNumberRepository repository;

    @Autowired
    private MasterPurchaseEntryRepository masterPurchaseEntryRepository;

    @Autowired
    private MasterSalesEntryRepository masterSalesEntryRepository;

    public List<DocumentNumber> getDocumentNumbers() {
        return repository.findAll();
    }

    public DocumentNumber saveDocumentNumber(DocumentNumber documentNumber) {
        if (repository.existsByModule(documentNumber.getModule().trim())) {
            throw new DuplicateResourceException("DocumentNumber with the same name already exists");
        }
        return repository.save(documentNumber);
    }

    public DocumentNumber updateDocumentNumber(int id, DocumentNumber documentNumber) {
        DocumentNumber existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DocumentNumber not found"));

        if (repository.existsByModuleAndIdNot(documentNumber.getModule().trim(), id)) {
            throw new DuplicateResourceException("DocumentNumber with the same name already exists");
        }
        return repository.save(existing);
    }

    public void deleteDocumentNumber(int id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("DocumentNumber not found"));
        repository.deleteById(id);
    }

    public String generatePurchaseNumber() {
        DocumentNumber pref = repository.findByModule("Purchase")
                .orElseThrow(() -> new RuntimeException("Document preferences not found for Purchase"));

        int nextNumber = pref.getStartNumber();
        Optional<MasterPurchaseEntry> lastEntryOpt = masterPurchaseEntryRepository.findTopByOrderByIdDesc();

        if (lastEntryOpt.isPresent()) {
            String lastNumber = lastEntryOpt.get().getSystemEntryNo();
            String numericPart = lastNumber.replace(pref.getPrefix(), "");
            try {
                nextNumber = Integer.parseInt(numericPart) + 1;
            } catch (NumberFormatException ignored) {
            }
        }

        if (nextNumber > pref.getEndNumber()) {
            throw new IllegalStateException("Document number has exceeded the configured end number.");
        }

        String formattedNumber = String.format("%0" + pref.getLength() + "d", nextNumber);
        return pref.getPrefix() + formattedNumber;
    }

    public String generateSalesNumber() {
        DocumentNumber pref = repository.findByModule("Sales")
                .orElseThrow(() -> new RuntimeException("Document preferences not found for Sales"));

        int nextNumber = pref.getStartNumber();
        Optional<MasterSalesEntry> lastEntryOpt = masterSalesEntryRepository.findTopByOrderByIdDesc();

        if (lastEntryOpt.isPresent()) {
            String lastNumber = lastEntryOpt.get().getSystemEntryNo();
            String numericPart = lastNumber.replace(pref.getPrefix(), "");
            try {
                nextNumber = Integer.parseInt(numericPart) + 1;
            } catch (NumberFormatException ignored) {
            }
        }

        if (nextNumber > pref.getEndNumber()) {
            throw new IllegalStateException("Document number has exceeded the configured end number.");
        }

        String formattedNumber = String.format("%0" + pref.getLength() + "d", nextNumber);
        return pref.getPrefix() + formattedNumber;
    }

    public String generateJournalNumber() {
        DocumentNumber pref = repository.findByModule("Journal")
                .orElseThrow(() -> new RuntimeException("Document preferences not found for Journal"));

        int nextNumber = pref.getStartNumber();
        Optional<MasterSalesEntry> lastEntryOpt = masterSalesEntryRepository.findTopByOrderByIdDesc();

        if (lastEntryOpt.isPresent()) {
            String lastNumber = lastEntryOpt.get().getSystemEntryNo();
            String numericPart = lastNumber.replace(pref.getPrefix(), "");
            try {
                nextNumber = Integer.parseInt(numericPart) + 1;
            } catch (NumberFormatException ignored) {
            }
        }

        if (nextNumber > pref.getEndNumber()) {
            throw new IllegalStateException("Document number has exceeded the configured end number.");
        }

        String formattedNumber = String.format("%0" + pref.getLength() + "d", nextNumber);
        return pref.getPrefix() + formattedNumber;
    }
}
