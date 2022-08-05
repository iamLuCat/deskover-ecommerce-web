package com.deskover.service;

import com.deskover.model.entity.database.PaymentMethods;

import java.util.List;

public interface PaymentService {
	List<PaymentMethods> getAll();
	PaymentMethods findById(Long id);



}
