package com.deskover.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.PaymentMethods;

public interface PaymentRepository extends JpaRepository<PaymentMethods, Long> {


}
