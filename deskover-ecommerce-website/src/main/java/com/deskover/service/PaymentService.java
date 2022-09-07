package com.deskover.service;

import java.util.List;

import com.deskover.entity.PaymentMethods;

public interface PaymentService {
	List<PaymentMethods> getAll();
	PaymentMethods findById(Long id);



}
