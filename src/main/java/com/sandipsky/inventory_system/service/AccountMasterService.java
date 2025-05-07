package com.sandipsky.inventory_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.inventory_system.dto.AccountMasterDTO;
import com.sandipsky.inventory_system.dto.filter.RequestDTO;
import com.sandipsky.inventory_system.entity.AccountMaster;
import com.sandipsky.inventory_system.exception.DuplicateResourceException;
import com.sandipsky.inventory_system.exception.ResourceNotFoundException;
import com.sandipsky.inventory_system.repository.AccountMasterRepository;
import com.sandipsky.inventory_system.util.SpecificationBuilder;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Service
public class AccountMasterService {

    @Autowired
    private AccountMasterRepository repository;

    private final SpecificationBuilder<AccountMaster> specBuilder = new SpecificationBuilder<>();

    public AccountMaster saveAccountMaster(AccountMasterDTO dto) {
        if (repository.existsByAccountCode(dto.getAccountCode())) {
            throw new DuplicateResourceException("Duplicate Account Code");
        }

        if (dto.getAccountName() == null || dto.getAccountName().trim().isEmpty()) {
            throw new RuntimeException("Account Name cannot be null or blank");
        }

        if (dto.getAccountType() == null || dto.getAccountType().trim().isEmpty()) {
            throw new RuntimeException("Account Type cannot be null or blank");
        }

        AccountMaster accountMaster = new AccountMaster();
        mapDtoToEntity(dto, accountMaster);
        return repository.save(accountMaster);
    }

    public Page<AccountMasterDTO> getPaginatedAccountMastersList(RequestDTO request) {
        Pageable pageable = PageRequest.of(
                request.getPagination() != null ? request.getPagination().getPageIndex() : 0,
                request.getPagination() != null ? request.getPagination().getPageSize() : 25,
                specBuilder.buildSort(request.getSortDTO()));

        Specification<AccountMaster> spec = specBuilder.buildSpecification(request.getFilter());
        Page<AccountMaster> accountMasterPage = repository.findAll(spec, pageable);
        return accountMasterPage.map(this::mapToDTO);
    }

    public List<AccountMasterDTO> getAccountMasters() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public AccountMasterDTO getAccountMasterById(int id) {
        AccountMaster accountMaster = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AccountMaster not found"));
        return mapToDTO(accountMaster);
    }

    public AccountMaster updateAccountMaster(int id, AccountMasterDTO accountMaster) {
        AccountMaster existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AccountMaster not found"));

        if (repository.existsByAccountCodeAndIdNot(accountMaster.getAccountCode(), existing.getId())) {
            throw new DuplicateResourceException("Duplicate Account Code");
        }

        if (accountMaster.getAccountName() == null || accountMaster.getAccountName().trim().isEmpty()) {
            throw new RuntimeException("Account Name cannot be null or blank");
        }

        if (accountMaster.getAccountType() == null || accountMaster.getAccountType().trim().isEmpty()) {
            throw new RuntimeException("Account Type cannot be null or blank");
        }

        mapDtoToEntity(accountMaster, existing);
        return repository.save(existing);
    }

    public void deleteAccountMaster(int id) {
        AccountMaster accountMaster = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AccountMaster not found"));
        if (accountMaster.getDeletable() == false) {
            throw new RuntimeException("Selected Account cannot be deleted");
        } else {
            repository.deleteById(id);
        }
    }

    private AccountMasterDTO mapToDTO(AccountMaster accountMaster) {
        AccountMasterDTO dto = new AccountMasterDTO();
        dto.setId(accountMaster.getId());
        dto.setAccountName(accountMaster.getAccountName());
        dto.setAccountCode(accountMaster.getAccountCode());
        dto.setAccountType(accountMaster.getAccountType());
        dto.setParentId(accountMaster.getParentId());
        dto.setParentAccountName(accountMaster.getParentAccountName());
        dto.setRemarks(accountMaster.getRemarks());
        dto.setIsActive(accountMaster.getIsActive());
        dto.setDeletable(accountMaster.getDeletable());
        return dto;
    }

    private void mapDtoToEntity(AccountMasterDTO dto, AccountMaster accountMaster) {
        accountMaster.setId(dto.getId());
        accountMaster.setAccountName(dto.getAccountName());
        accountMaster.setAccountCode(dto.getAccountCode());
        accountMaster.setAccountType(dto.getAccountType());
        accountMaster.setParentId(dto.getParentId());
        accountMaster.setParentAccountName(dto.getParentAccountName());
        accountMaster.setRemarks(dto.getRemarks());
        accountMaster.setIsActive(dto.getIsActive());
        accountMaster.setDeletable(dto.getDeletable());
    }
}
