package com.deskover.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.entity.StatusPayment;
import com.deskover.reponsitory.StatusPaymentRepository;
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
