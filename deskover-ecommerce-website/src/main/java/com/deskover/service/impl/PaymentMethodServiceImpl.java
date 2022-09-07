package com.deskover.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.entity.PaymentMethods;
import com.deskover.reponsitory.PaymentRepository;
import com.deskover.service.PaymentService;

@Service
public class PaymentMethodServiceImpl implements PaymentService {
	@Autowired
	private PaymentRepository paymentRepository;

	@Override
	public List<PaymentMethods> getAll() {
		return paymentRepository.findAll();
	}

	@Override
	public PaymentMethods findById(Long id) {
		return paymentRepository.getById(id);
	}


}
