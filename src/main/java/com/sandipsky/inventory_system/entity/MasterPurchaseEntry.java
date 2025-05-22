package com.sandipsky.inventory_system.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "master_purchase_entry")
public class MasterPurchaseEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@JsonProperty("date")
	private String date;
	
	@JsonProperty("system_entry_no")
	@Column(columnDefinition = "varchar(25) unique not null")
	private String systemEntryNo;
	
	@JsonProperty("bill_no")
	private String billNo;
	
	@JsonProperty("transaction_type")
	private String transactionType;
	
	@JsonProperty("sub_total")
	@Column(columnDefinition = "double default 0 not null")
	private double subTotal;
	
	@Column(columnDefinition = "double default 0 not null")
	private double discount;
	
	@JsonProperty("non_taxable_amount")
	@Column(columnDefinition = "double default 0 not null")
	private double nonTaxableAmount; 
	
	@JsonProperty("taxable_amount")
	@Column(columnDefinition = "double default 0 not null")
	private double taxableAmount;
	
	@JsonProperty("total_tax")
	@Column(columnDefinition = "double default 0 not null")
	private double totalTax;
	
	@Column(columnDefinition = "boolean default false")
	private boolean rounded;
	
	@JsonProperty("rounding")
	@Column(columnDefinition = "double default 0 not null")
	private double rounding;
	
	@JsonProperty("grand_total")
	@Column(columnDefinition = "double default 0 not null")
	private double grandTotal;
	
	@JsonProperty("discount_type")
	private String discountType;
	
	@Column(columnDefinition = "TEXT")
	private String remarks;
	
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "masterPurchaseEntryId", referencedColumnName = "id")
    private List<PurchaseEntry> purchaseEntries;
	
	@ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;
}