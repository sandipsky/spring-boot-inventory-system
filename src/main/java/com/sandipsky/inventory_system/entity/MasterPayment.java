package com.sandipsky.inventory_system.entity;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "master_payment")
public class MasterPayment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String date;

	@Column(columnDefinition = "varchar(25) unique not null")
	private String systemEntryNo;

	private String type;

	@Column(columnDefinition = "DOUBLE NOT NULL DEFAULT 0")
	private double amount;

	@Column(columnDefinition = "TEXT")
	private String narration;

	@Column(columnDefinition = "double not null default 0")
	private double totalAdjustedPaidAmount;

	@Column(columnDefinition = "double not null default 0")
	private double unadjustedAmount;

	@Column(columnDefinition = "double not null default 0")
	private double totalPaymentAmount;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "masterPaymentId", referencedColumnName = "id")
	private List<PaymentDetail> paymentDetails;

	@ManyToOne
	@JoinColumn(name = "party_id", insertable = true, updatable = true)
	private Party party;

	@ManyToOne
	@JoinColumn(name = "transaction_type", insertable = true, updatable = true)
	private AccountMaster paymentMode;
}