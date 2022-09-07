package com.deskover.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.entity.ShippingMethods;
import com.deskover.reponsitory.ShippingRepository;
import com.deskover.service.ShippingService;

@Service
public class ShippingServiceImpl implements ShippingService {
	@Autowired
	private ShippingRepository shippingRepository;

	@Override
	public List<ShippingMethods> getAll() {
		return shippingRepository.findAll();
	}

	@Override
	public ShippingMethods findById(Long id) {
		return shippingRepository.getById(id);
	}

}
