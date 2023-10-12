package com.ecommerce.billingservice.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Class representing a bill in e-commerce application.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDto {

	@ApiModelProperty(notes = "Unique identifier of the Bill.", example = "1")
	private Long id;

	@ApiModelProperty(notes = "Bill number.", example = "278623", required = true)
	@NotNull
	private String billNo;

	@ApiModelProperty(notes = "Customer name.", example = "Abhishek", required = true)
	private String customerName;

	@NotNull
	private Long orderId;

	@ApiModelProperty(notes = "Total amount of the order.", example = "450.00", required = true)
	@NotNull
	private BigDecimal total;

	@ApiModelProperty(notes = "Tax amount.")
	private BigDecimal taxAmount;

	@ApiModelProperty(notes = "Status of the bill.", example = "PAID", required = true)
	@NotBlank
	private String billStatus;
	

	public BillDto(String billNo, Long orderId, BigDecimal total, String billStatus) {
		this.billNo = billNo;
		this.orderId = orderId;
		this.total = total;
		this.billStatus = billStatus;
	}
}
