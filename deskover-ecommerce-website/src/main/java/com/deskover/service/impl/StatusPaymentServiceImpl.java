package com.deskover.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.model.entity.database.StatusPayment;
import com.deskover.model.entity.database.repository.StatusPaymentRepository;
import com.deskover.service.StatusPaymentService;

@Service
public class StatusPaymentServiceImpl implements StatusPaymentService{
	@Autowired 
	private StatusPaymentRepository repo;
	
	@Override
	public StatusPayment findByCode(String code) {
		return repo.findByCode(code);
	}

}
