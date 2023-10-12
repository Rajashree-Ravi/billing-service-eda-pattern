package com.ecommerce.billingservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.billingservice.exception.EcommerceException;
import com.ecommerce.billingservice.model.BillDto;
import com.ecommerce.billingservice.service.BillingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(produces = "application/json", value = "Operations pertaining to manage bills in e-commerce application")
@RequestMapping("/api/bills")
public class BillingController {

	@Autowired
	BillingService billingService;

	@GetMapping
	@ApiOperation(value = "View all bill", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved all bill"),
			@ApiResponse(code = 204, message = "Bill list is empty"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<List<BillDto>> getAllBill() {

		List<BillDto> bills = billingService.getAllBills();
		if (bills.isEmpty())
			throw new EcommerceException("no-content", "Bill list is empty", HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(bills, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Retrieve specific bill with the specified bill id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved bill with the bill id"),
			@ApiResponse(code = 404, message = "Bill with specified bill id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<BillDto> getBillById(@PathVariable("id") long id) {

		BillDto bill = billingService.getBillById(id);
		if (bill != null)
			return new ResponseEntity<>(bill, HttpStatus.OK);
		else
			throw new EcommerceException("bill-not-found", String.format("Bill with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@PostMapping
	@ApiOperation(value = "Create a new bill", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created a bill"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<BillDto> createBill(@RequestBody BillDto bill) {
		return new ResponseEntity<>(billingService.createBill(bill), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Update a bill information", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated bill information"),
			@ApiResponse(code = 404, message = "Bill with specified bill id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<BillDto> updateBill(@PathVariable("id") long id, @RequestBody BillDto bill) {

		BillDto updatedBill = billingService.updateBill(id, bill);
		if (updatedBill != null)
			return new ResponseEntity<>(updatedBill, HttpStatus.OK);
		else
			throw new EcommerceException("bill-not-found", String.format("Bill with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a bill", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully deleted bill information"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<String> deleteBill(@PathVariable("id") long id) {

		billingService.deleteBill(id);
		return new ResponseEntity<>("Bill deleted successfully", HttpStatus.NO_CONTENT);
	}
}
