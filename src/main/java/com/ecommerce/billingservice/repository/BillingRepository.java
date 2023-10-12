package com.ecommerce.billingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.billingservice.entity.Bill;

public interface BillingRepository extends JpaRepository<Bill, Long> {

}
