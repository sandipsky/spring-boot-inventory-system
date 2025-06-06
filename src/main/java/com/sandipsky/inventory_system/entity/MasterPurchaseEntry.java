package com.sandipsky.inventory_system.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "master_purchase_entry", uniqueConstraints = {
		@UniqueConstraint(name = "unique_party_bill_no", columnNames = { "billNo", "vendor_id" }) })
public class MasterPurchaseEntry {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String date;

	@Column(columnDefinition = "varchar(25) unique not null")
	private String systemEntryNo;

	@Column(name = "bill_no", nullable = false)
	private String billNo;

	private String transactionType;

	@Column(columnDefinition = "double default 0 not null")
	private double subTotal;

	@Column(columnDefinition = "double default 0 not null")
	private double discount;

	@Column(columnDefinition = "double default 0 not null")
	private double nonTaxableAmount;

	@Column(columnDefinition = "double default 0 not null")
	private double taxableAmount;

	@Column(columnDefinition = "double default 0 not null")
	private double totalTax;

	@Column(columnDefinition = "boolean default false")
	private boolean rounded;

	@Column(columnDefinition = "double default 0 not null")
	private double rounding;

	@Column(columnDefinition = "double default 0 not null")
	private double grandTotal;

	private String discountType;

	@Column(columnDefinition = "TEXT")
	private String remarks;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "masterPurchaseEntryId", referencedColumnName = "id")
	private List<PurchaseEntry> purchaseEntries;

	@ManyToOne
	@JoinColumn(name = "party_id")
	private Party party;
}