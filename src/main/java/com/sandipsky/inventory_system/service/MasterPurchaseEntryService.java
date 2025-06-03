package com.sandipsky.inventory_system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sandipsky.inventory_system.dto.purchase.MasterPurchaseEntryDTO;
import com.sandipsky.inventory_system.dto.purchase.PurchaseEntryDTO;
import com.sandipsky.inventory_system.dto.filter.RequestDTO;
import com.sandipsky.inventory_system.entity.MasterPurchaseEntry;
import com.sandipsky.inventory_system.entity.Party;
import com.sandipsky.inventory_system.entity.Product;
import com.sandipsky.inventory_system.entity.ProductStock;
import com.sandipsky.inventory_system.entity.PurchaseEntry;
import com.sandipsky.inventory_system.exception.ResourceNotFoundException;
import com.sandipsky.inventory_system.repository.MasterPurchaseEntryRepository;
import com.sandipsky.inventory_system.repository.PartyRepository;
import com.sandipsky.inventory_system.repository.ProductRepository;
import com.sandipsky.inventory_system.repository.ProductStockRepository;
import com.sandipsky.inventory_system.repository.PurchaseEntryRepository;
import com.sandipsky.inventory_system.util.SpecificationBuilder;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

@Service
public class MasterPurchaseEntryService {

    @Autowired
    private MasterPurchaseEntryRepository repository;

    @Autowired
    private PurchaseEntryRepository purchaseEntryRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductStockRepository productStockRepository;

    @Autowired
    private DocumentNumberService documentNumberService;

    private final SpecificationBuilder<MasterPurchaseEntry> specBuilder = new SpecificationBuilder<>();

    public Page<MasterPurchaseEntryDTO> getPaginatedMasterPurchaseEntrysList(RequestDTO request) {
        Pageable pageable = PageRequest.of(
                request.getPagination() != null ? request.getPagination().getPageIndex() : 0,
                request.getPagination() != null ? request.getPagination().getPageSize() : 25,
                specBuilder.buildSort(request.getSortDTO()));

        Specification<MasterPurchaseEntry> spec = specBuilder.buildSpecification(request.getFilter());
        Page<MasterPurchaseEntry> productPage = repository.findAll(spec, pageable);
        return productPage.map(this::mapToDTO);
    }

    public MasterPurchaseEntryDTO getMasterPurchaseEntryById(int id) {
        MasterPurchaseEntry masterPurchaseEntry = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Entry with Given Id not found"));
        MasterPurchaseEntryDTO masterPurchaseEntryDTO = new MasterPurchaseEntryDTO();
        masterPurchaseEntryDTO.setId(masterPurchaseEntry.getId());
        masterPurchaseEntryDTO.setDate(masterPurchaseEntry.getDate());
        masterPurchaseEntryDTO.setSystemEntryNo(masterPurchaseEntry.getSystemEntryNo());
        masterPurchaseEntryDTO.setBillNo(masterPurchaseEntry.getBillNo());
        masterPurchaseEntryDTO.setTransactionType(masterPurchaseEntry.getTransactionType());
        masterPurchaseEntryDTO.setSubTotal(masterPurchaseEntry.getSubTotal());
        masterPurchaseEntryDTO.setDiscount(masterPurchaseEntry.getDiscount());
        masterPurchaseEntryDTO.setNonTaxableAmount(masterPurchaseEntry.getNonTaxableAmount());
        masterPurchaseEntryDTO.setTaxableAmount(masterPurchaseEntry.getTaxableAmount());
        masterPurchaseEntryDTO.setTotalTax(masterPurchaseEntry.getTotalTax());
        masterPurchaseEntryDTO.setRounded(masterPurchaseEntry.isRounded());
        masterPurchaseEntryDTO.setRounding(masterPurchaseEntry.getRounding());
        masterPurchaseEntryDTO.setGrandTotal(masterPurchaseEntry.getGrandTotal());
        masterPurchaseEntryDTO.setDiscountType(masterPurchaseEntry.getDiscountType());
        masterPurchaseEntryDTO.setRemarks(masterPurchaseEntry.getRemarks());

        if (masterPurchaseEntry.getPurchaseEntries() != null) {
            masterPurchaseEntryDTO.setPurchaseEntries(
                    masterPurchaseEntry.getPurchaseEntries().stream()
                            .map(pe -> {
                                PurchaseEntryDTO pedto = new PurchaseEntryDTO();
                                pedto.setId(pe.getId());
                                pedto.setQuantity(pe.getQuantity());
                                pedto.setCostPrice(pe.getCostPrice());
                                pedto.setSellingPrice(pe.getSellingPrice());
                                pedto.setMasterPurchaseEntryId(pe.getMasterPurchaseEntryId());
                                pedto.setMrp(pe.getMrp());
                                pedto.setProductId(pe.getProduct().getId());
                                pedto.setProductName(pe.getProduct().getName());
                                return pedto;
                            }).toList());
        }
        return masterPurchaseEntryDTO;
    }

    @Transactional
    public MasterPurchaseEntry saveMasterPurchaseEntry(MasterPurchaseEntryDTO masterPurchaseEntryDTO) {
        MasterPurchaseEntry masterPurchaseEntry = new MasterPurchaseEntry();
        masterPurchaseEntry.setDate(masterPurchaseEntryDTO.getDate());
        masterPurchaseEntry.setSystemEntryNo(documentNumberService.generatePurchaseNumber());
        masterPurchaseEntry.setBillNo(masterPurchaseEntryDTO.getBillNo());
        masterPurchaseEntry.setTransactionType(masterPurchaseEntryDTO.getTransactionType());
        masterPurchaseEntry.setSubTotal(masterPurchaseEntryDTO.getSubTotal());
        masterPurchaseEntry.setDiscount(masterPurchaseEntryDTO.getDiscount());
        masterPurchaseEntry.setNonTaxableAmount(masterPurchaseEntryDTO.getNonTaxableAmount());
        masterPurchaseEntry.setTaxableAmount(masterPurchaseEntryDTO.getTaxableAmount());
        masterPurchaseEntry.setTotalTax(masterPurchaseEntryDTO.getTotalTax());
        masterPurchaseEntry.setRounded(masterPurchaseEntryDTO.isRounded());
        masterPurchaseEntry.setRounding(masterPurchaseEntryDTO.getRounding());
        masterPurchaseEntry.setGrandTotal(masterPurchaseEntryDTO.getGrandTotal());
        masterPurchaseEntry.setDiscountType(masterPurchaseEntryDTO.getDiscountType());
        Party party = partyRepository.findById(masterPurchaseEntryDTO.getPartyId())
                .orElseThrow(() -> new ResourceNotFoundException("Party not found"));
        masterPurchaseEntry.setParty(party);

        if (masterPurchaseEntryDTO.getRemarks().isEmpty() || masterPurchaseEntryDTO.getRemarks() == null) {
            masterPurchaseEntry.setRemarks("Purchased Goods from " + party.getName());
        } else {
            masterPurchaseEntry.setRemarks(masterPurchaseEntryDTO.getRemarks());
        }

        MasterPurchaseEntry savedEntry = new MasterPurchaseEntry();

        try {
            savedEntry = repository.save(masterPurchaseEntry);
        } catch (Exception ex) {
            if (ex.getMessage().contains("unique_party_bill_no")) {
                throw new RuntimeException("Bill Number cannot be repeated for the same Vendor");
            }
        }

        if (masterPurchaseEntryDTO.getPurchaseEntries() != null) {
            for (PurchaseEntryDTO item : masterPurchaseEntryDTO.getPurchaseEntries()) {
                // Saving purchase Entry
                PurchaseEntry purchaseEntry = new PurchaseEntry();
                purchaseEntry.setQuantity(item.getQuantity());
                purchaseEntry.setCostPrice(item.getCostPrice());
                purchaseEntry.setSellingPrice(item.getSellingPrice());
                purchaseEntry.setMrp(item.getMrp());
                purchaseEntry.setMasterPurchaseEntryId(savedEntry.getId());
                purchaseEntryRepository.save(purchaseEntry);

                // Updating Product Master
                Product product = productRepository.findById(item.getProductId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
                product.setCostPrice(item.getCostPrice());
                product.setSellingPrice(item.getSellingPrice());
                product.setMrp(item.getMrp());
                purchaseEntry.setProduct(product);
                productRepository.save(product);

                // Creating Product Stock
                ProductStock productStock = productStockRepository.findByProductId(product.getId());
                if (productStock != null) {
                    // Update existing stock
                    productStock.setQuantity(productStock.getQuantity() + item.getQuantity());
                    productStock.setCostPrice(item.getCostPrice());
                    productStock.setSellingPrice(item.getSellingPrice());
                    productStock.setMrp(item.getMrp());
                } else {
                    // Create new stock
                    productStock = new ProductStock();
                    productStock.setProduct(product);
                    productStock.setQuantity(item.getQuantity());
                    productStock.setCostPrice(item.getCostPrice());
                    productStock.setSellingPrice(item.getSellingPrice());
                    productStock.setMrp(item.getMrp());
                }
                productStockRepository.save(productStock);
            }
        }

        return savedEntry;
    }

    @Transactional
    public MasterPurchaseEntry updateMasterPurchaseEntry(int id, MasterPurchaseEntryDTO masterPurchaseEntryDTO) {
        MasterPurchaseEntry masterPurchaseEntry = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Entry with Given Id not found"));
        masterPurchaseEntry.setDate(masterPurchaseEntryDTO.getDate());
        masterPurchaseEntry.setBillNo(masterPurchaseEntryDTO.getBillNo());
        masterPurchaseEntry.setTransactionType(masterPurchaseEntryDTO.getTransactionType());
        masterPurchaseEntry.setSubTotal(masterPurchaseEntryDTO.getSubTotal());
        masterPurchaseEntry.setDiscount(masterPurchaseEntryDTO.getDiscount());
        masterPurchaseEntry.setNonTaxableAmount(masterPurchaseEntryDTO.getNonTaxableAmount());
        masterPurchaseEntry.setTaxableAmount(masterPurchaseEntryDTO.getTaxableAmount());
        masterPurchaseEntry.setTotalTax(masterPurchaseEntryDTO.getTotalTax());
        masterPurchaseEntry.setRounded(masterPurchaseEntryDTO.isRounded());
        masterPurchaseEntry.setRounding(masterPurchaseEntryDTO.getRounding());
        masterPurchaseEntry.setGrandTotal(masterPurchaseEntryDTO.getGrandTotal());
        masterPurchaseEntry.setDiscountType(masterPurchaseEntryDTO.getDiscountType());
        Party party = partyRepository.findById(masterPurchaseEntryDTO.getPartyId())
                .orElseThrow(() -> new ResourceNotFoundException("Party not found"));
        masterPurchaseEntry.setParty(party);

        if (masterPurchaseEntryDTO.getRemarks().isEmpty() || masterPurchaseEntryDTO.getRemarks() == null) {
            masterPurchaseEntry.setRemarks("Purchased Goods from " + party.getName());
        } else {
            masterPurchaseEntry.setRemarks(masterPurchaseEntryDTO.getRemarks());
        }

        MasterPurchaseEntry savedEntry = new MasterPurchaseEntry();

        try {
            savedEntry = repository.save(masterPurchaseEntry);
        } catch (Exception ex) {
            if (ex.getMessage().contains("unique_party_bill_no")) {
                throw new RuntimeException("Bill Number cannot be repeated for the same Vendor");
            }
        }

        List<PurchaseEntry> existingEntries = purchaseEntryRepository.findByMasterPurchaseEntryId(savedEntry.getId());
        List<Integer> incomingIds = new ArrayList<>();
        for (PurchaseEntryDTO dto : masterPurchaseEntryDTO.getPurchaseEntries()) {
            incomingIds.add(dto.getId());
        }

        for (PurchaseEntry existing : existingEntries) {
            boolean found = false;
            for (Integer entryId : incomingIds) {
                if (existing.getId() == entryId) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                ProductStock stock = productStockRepository.findByProductId(existing.getProduct().getId());
                Double newStock = stock.getQuantity() - existing.getQuantity();
                if (newStock > 0) {
                    stock.setQuantity(newStock);
                } else {
                    throw new RuntimeException("Product Stock is Already Used");
                }
                productStockRepository.save(stock);
                purchaseEntryRepository.delete(existing);
            }
        }

        if (masterPurchaseEntryDTO.getPurchaseEntries() != null) {
            for (PurchaseEntryDTO item : masterPurchaseEntryDTO.getPurchaseEntries()) {
                // Saving purchase Entry
                PurchaseEntry purchaseEntry;
                if (item.getId() != 0) {
                    purchaseEntry = purchaseEntryRepository.findById(item.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Purchase Entry not found"));
                } else {
                    purchaseEntry = new PurchaseEntry();
                }
                Double tempQuantity = purchaseEntry.getQuantity();
                purchaseEntry.setQuantity(item.getQuantity());
                purchaseEntry.setCostPrice(item.getCostPrice());
                purchaseEntry.setSellingPrice(item.getSellingPrice());
                purchaseEntry.setMrp(item.getMrp());
                purchaseEntry.setMasterPurchaseEntryId(savedEntry.getId());
                purchaseEntryRepository.save(purchaseEntry);

                // Updating Product Master
                Product product = productRepository.findById(item.getProductId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
                product.setCostPrice(item.getCostPrice());
                product.setSellingPrice(item.getSellingPrice());
                product.setMrp(item.getMrp());
                purchaseEntry.setProduct(product);
                productRepository.save(product);

                // Creating Product Stock
                ProductStock productStock = productStockRepository.findByProductId(product.getId());
                if (productStock != null) {
                    // Update existing stock
                    Double newStock = productStock.getQuantity() - tempQuantity + item.getQuantity();
                    if (newStock > 0) {
                        productStock.setQuantity(newStock);
                    } else {
                        throw new RuntimeException("Product Stock is Already Used");
                    }
                    productStock.setCostPrice(item.getCostPrice());
                    productStock.setSellingPrice(item.getSellingPrice());
                    productStock.setMrp(item.getMrp());
                } else {
                    // Create new stock
                    productStock = new ProductStock();
                    productStock.setProduct(product);
                    productStock.setQuantity(item.getQuantity());
                    productStock.setCostPrice(item.getCostPrice());
                    productStock.setSellingPrice(item.getSellingPrice());
                    productStock.setMrp(item.getMrp());
                }
                productStockRepository.save(productStock);
            }
        }

        return savedEntry;
    }

    @Transactional
    public void deleteMasterPurchaseEntry(int id) {
        MasterPurchaseEntry masterPurchaseEntry = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Entry with Given Id not found"));
        for (PurchaseEntry item : masterPurchaseEntry.getPurchaseEntries()) {
            ProductStock productStock = productStockRepository.findByProductId(item.getProduct().getId());
            Double newStock = productStock.getQuantity() - item.getQuantity();
            if (newStock > 0) {
                productStock.setQuantity(newStock);
            } else {
                throw new RuntimeException("Product Stock is Already Used");
            }
            productStockRepository.save(productStock);
            purchaseEntryRepository.deleteById(item.getId());
        }
        repository.deleteById(id);
    }

    private MasterPurchaseEntryDTO mapToDTO(MasterPurchaseEntry entity) {
        MasterPurchaseEntryDTO masterPurchaseEntryDTO = new MasterPurchaseEntryDTO();
        masterPurchaseEntryDTO.setId(entity.getId());
        masterPurchaseEntryDTO.setDate(entity.getDate());
        masterPurchaseEntryDTO.setSystemEntryNo(entity.getSystemEntryNo());
        masterPurchaseEntryDTO.setBillNo(entity.getBillNo());
        masterPurchaseEntryDTO.setTransactionType(entity.getTransactionType());
        masterPurchaseEntryDTO.setSubTotal(entity.getSubTotal());
        masterPurchaseEntryDTO.setDiscount(entity.getDiscount());
        masterPurchaseEntryDTO.setNonTaxableAmount(entity.getNonTaxableAmount());
        masterPurchaseEntryDTO.setTaxableAmount(entity.getTaxableAmount());
        masterPurchaseEntryDTO.setTotalTax(entity.getTotalTax());
        masterPurchaseEntryDTO.setRounded(entity.isRounded());
        masterPurchaseEntryDTO.setRounding(entity.getRounding());
        masterPurchaseEntryDTO.setGrandTotal(entity.getGrandTotal());
        masterPurchaseEntryDTO.setDiscountType(entity.getDiscountType());
        masterPurchaseEntryDTO.setRemarks(entity.getRemarks());
        return masterPurchaseEntryDTO;
    }

}
