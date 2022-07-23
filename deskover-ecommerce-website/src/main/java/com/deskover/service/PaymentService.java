package com.deskover.service;

import java.util.List;

import com.deskover.entity.PaymentMethods;

public interface PaymentService {
	List<PaymentMethods> doGetAll();
	PaymentMethods findById(Long id);

}
