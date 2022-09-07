package com.deskover.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.StatusPayment;

public interface StatusPaymentRepository extends JpaRepository<StatusPayment, Long>{
	StatusPayment findByCode(String code);
}
