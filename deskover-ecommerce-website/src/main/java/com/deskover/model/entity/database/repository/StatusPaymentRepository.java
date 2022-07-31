package com.deskover.model.entity.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.StatusPayment;

public interface StatusPaymentRepository extends JpaRepository<StatusPayment, Long>{
	StatusPayment findByCode(String code);
}
