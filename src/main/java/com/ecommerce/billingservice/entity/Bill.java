package com.ecommerce.billingservice.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bill")
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String billNo;

	private String customerName;

	@NotNull
	private Long orderId;

	@NotNull
	private BigDecimal total;

	private BigDecimal taxAmount;

	@NotBlank
	private String billStatus;

	public Bill updateWith(Bill bill) {
		return new Bill(this.id, bill.billNo, bill.customerName, bill.orderId, bill.total, bill.taxAmount,
				bill.billStatus);
	}
}
