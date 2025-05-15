package com.sandipsky.inventory_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.inventory_system.dto.purchase.MasterPurchaseEntryDTO;
import com.sandipsky.inventory_system.dto.purchase.PurchaseEntryDTO;
import com.sandipsky.inventory_system.dto.ProductDTO;
import com.sandipsky.inventory_system.dto.filter.RequestDTO;
import com.sandipsky.inventory_system.entity.Category;
import com.sandipsky.inventory_system.entity.MasterPurchaseEntry;
import com.sandipsky.inventory_system.entity.Product;
import com.sandipsky.inventory_system.entity.PurchaseEntry;
import com.sandipsky.inventory_system.entity.Unit;
import com.sandipsky.inventory_system.exception.DuplicateResourceException;
import com.sandipsky.inventory_system.exception.ResourceNotFoundException;
import com.sandipsky.inventory_system.repository.CategoryRepository;
import com.sandipsky.inventory_system.repository.MasterPurchaseEntryRepository;
import com.sandipsky.inventory_system.repository.PartyRepository;
import com.sandipsky.inventory_system.repository.MasterPurchaseEntryRepository;
import com.sandipsky.inventory_system.repository.UnitRepository;
import com.sandipsky.inventory_system.util.SpecificationBuilder;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Service
public class MasterPurchaseEntryService {

    @Autowired
    private MasterPurchaseEntryRepository repository;

    @Autowired
    private PartyRepository partyRepository;

    private final SpecificationBuilder<MasterPurchaseEntry> specBuilder = new SpecificationBuilder<>();

    public MasterPurchaseEntry saveMasterPurchaseEntry(MasterPurchaseEntryDTO dto) {
        // if (repository.existsByName(dto.getName())) {
        // throw new DuplicateResourceException("MasterPurchaseEntry with the same name
        // already exists");
        // }

        // if (dto.getName() == null || dto.getName().trim().isEmpty()) {
        // throw new RuntimeException("MasterPurchaseEntry name cannot be null or
        // blank");
        // }

        MasterPurchaseEntry product = new MasterPurchaseEntry();
        mapDtoToEntity(dto, product);
        return repository.save(product);
    }

    public Page<MasterPurchaseEntryDTO> getPaginatedMasterPurchaseEntrysList(RequestDTO request) {
        Pageable pageable = PageRequest.of(
                request.getPagination() != null ? request.getPagination().getPageIndex() : 0,
                request.getPagination() != null ? request.getPagination().getPageSize() : 25,
                specBuilder.buildSort(request.getSortDTO()));

        Specification<MasterPurchaseEntry> spec = specBuilder.buildSpecification(request.getFilter());
        Page<MasterPurchaseEntry> productPage = repository.findAll(spec, pageable);
        return productPage.map(this::mapToDTO);
    }

    public List<MasterPurchaseEntryDTO> getMasterPurchaseEntrys() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public MasterPurchaseEntryDTO getMasterPurchaseEntryById(int id) {
        MasterPurchaseEntry product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MasterPurchaseEntry not found"));
        return mapToDTO(product);
    }

    public MasterPurchaseEntry updateMasterPurchaseEntry(int id, MasterPurchaseEntryDTO product) {
        MasterPurchaseEntry existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MasterPurchaseEntry not found"));

        // if (product.getName() == null || product.getName().trim().isEmpty()) {
        // throw new RuntimeException("MasterPurchaseEntry name cannot be null or
        // blank");
        // }

        // if (repository.existsByNameAndIdNot(product.getName(), id)) {
        // throw new DuplicateResourceException("MasterPurchaseEntry with the same name
        // already exists");
        // }

        mapDtoToEntity(product, existing);
        return repository.save(existing);
    }

    public void deleteMasterPurchaseEntry(int id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("MasterPurchaseEntry not found"));
        repository.deleteById(id);
    }

    private MasterPurchaseEntryDTO mapToDTO(MasterPurchaseEntry entity) {
        MasterPurchaseEntryDTO dto = new MasterPurchaseEntryDTO();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setSystemEntryNo(entity.getSystemEntryNo());
        dto.setBillNo(entity.getBillNo());
        dto.setTransactionType(entity.getTransactionType());
        dto.setSubTotal(entity.getSubTotal());
        dto.setDiscount(entity.getDiscount());
        dto.setNonTaxableAmount(entity.getNonTaxableAmount());
        dto.setTaxableAmount(entity.getTaxableAmount());
        dto.setTotalTax(entity.getTotalTax());
        dto.setRounded(entity.isRounded());
        dto.setRounding(entity.getRounding());
        dto.setGrandTotal(entity.getGrandTotal());
        dto.setDiscountType(entity.getDiscountType());
        dto.setRemarks(entity.getRemarks());

        if (entity.getPurchaseEntries() != null) {
            dto.setPurchaseEntries(
                    entity.getPurchaseEntries().stream()
                            .map(pe -> {
                                PurchaseEntryDTO pedto = new PurchaseEntryDTO();
                                pedto.setId(pe.getId());
                                pedto.setBatch(pe.getBatch());
                                pedto.setCostPrice(pe.getCostPrice());
                                pedto.setSellingPrice(pe.getSellingPrice());
                                pedto.setMrp(pe.getMrp());
                                if (pe.getProduct() != null) {
                                    ProductDTO pdto = new ProductDTO();
                                    pdto.setId(pe.getProduct().getId());
                                    pdto.setName(pe.getProduct().getName());
                                    // pedto.setProduct(pdto);
                                }
                                return pedto;
                            }).toList());
        }

        return dto;
    }

    private void mapDtoToEntity(MasterPurchaseEntryDTO dto, MasterPurchaseEntry entity) {
        entity.setDate(dto.getDate());
        entity.setSystemEntryNo(dto.getSystemEntryNo());
        entity.setBillNo(dto.getBillNo());
        entity.setTransactionType(dto.getTransactionType());
        entity.setSubTotal(dto.getSubTotal());
        entity.setDiscount(dto.getDiscount());
        entity.setNonTaxableAmount(dto.getNonTaxableAmount());
        entity.setTaxableAmount(dto.getTaxableAmount());
        entity.setTotalTax(dto.getTotalTax());
        entity.setRounded(dto.isRounded());
        entity.setRounding(dto.getRounding());
        entity.setGrandTotal(dto.getGrandTotal());
        entity.setDiscountType(dto.getDiscountType());
        entity.setRemarks(dto.getRemarks());

        if (dto.getPurchaseEntries() != null) {
            List<PurchaseEntry> purchaseEntries = dto.getPurchaseEntries().stream().map(pedto -> {
                PurchaseEntry pe = new PurchaseEntry();
                pe.setBatch(pedto.getBatch());
                pe.setCostPrice(pedto.getCostPrice());
                pe.setSellingPrice(pedto.getSellingPrice());
                pe.setMrp(pedto.getMrp());
                // if (pedto.getProduct() != null && pedto.getProduct().getId() != null) {
                //     Product p = new Product();
                //     p.setId(pedto.getProduct().getId());
                //     pe.setProduct(p);
                // }
                return pe;
            }).toList();
            entity.setPurchaseEntries(purchaseEntries);
        }

        // if (dto.getPartyId() != null) {
        //     entity.setParty(partyRepository.findById(dto.getPartyId())
        //             .orElseThrow(() -> new ResourceNotFoundException("Party not found")));
        // }
    }
}
