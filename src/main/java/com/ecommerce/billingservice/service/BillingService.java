package com.ecommerce.billingservice.service;

import java.util.List;

import com.ecommerce.billingservice.model.BillDto;

public interface BillingService {

	List<BillDto> getAllBills();

	BillDto getBillById(long id);

	BillDto createBill(BillDto bill);

	BillDto updateBill(long id, BillDto bill);

	void deleteBill(long id);
}
