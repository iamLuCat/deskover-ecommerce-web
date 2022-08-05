package com.deskover.model.entity.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.PaymentMethods;

public interface PaymentRepository extends JpaRepository<PaymentMethods, Long> {


}
