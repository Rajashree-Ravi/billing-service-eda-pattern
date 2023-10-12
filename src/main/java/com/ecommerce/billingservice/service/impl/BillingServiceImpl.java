package com.ecommerce.billingservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecommerce.billingservice.entity.Bill;
import com.ecommerce.billingservice.exception.EcommerceException;
import com.ecommerce.billingservice.model.BillDto;
import com.ecommerce.billingservice.repository.BillingRepository;
import com.ecommerce.billingservice.service.BillingService;

@Service
public class BillingServiceImpl implements BillingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BillingServiceImpl.class);

	@Autowired
	private BillingRepository billingRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public List<BillDto> getAllBills() {
		List<BillDto> bills = new ArrayList<>();
		billingRepository.findAll().forEach(bill -> {
			bills.add(mapper.map(bill, BillDto.class));
		});
		return bills;
	}

	@Override
	public BillDto getBillById(long id) {
		Optional<Bill> bill = billingRepository.findById(id);
		return (bill.isPresent() ? mapper.map(bill.get(), BillDto.class) : null);
	}

	@Override
	public BillDto createBill(BillDto billDto) {
		Bill bill = mapper.map(billDto, Bill.class);
		return mapper.map(billingRepository.save(bill), BillDto.class);
	}

	@Override
	public BillDto updateBill(long id, BillDto billDto) {
		Optional<Bill> updatedBill = billingRepository.findById(id).map(existingBill -> {
			Bill bill = mapper.map(billDto, Bill.class);
			return billingRepository.save(existingBill.updateWith(bill));
		});

		return (updatedBill.isPresent() ? mapper.map(updatedBill.get(), BillDto.class) : null);
	}

	@Override
	public void deleteBill(long id) {
		if (getBillById(id) != null) {
			billingRepository.deleteById(id);
			LOGGER.info("Bill deleted Successfully");
		} else {
			throw new EcommerceException("bill-not-found", String.format("Bill with id=%d not found", id),
					HttpStatus.NOT_FOUND);
		}
	}
}
