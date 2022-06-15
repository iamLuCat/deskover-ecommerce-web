package com.deskover.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.entity.Discount;
import com.deskover.entity.Subcategory;
import com.deskover.repository.DiscountRepository;
import com.deskover.service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService {
	
	@Autowired
	private DiscountRepository repository;

	@Override
	public List<Discount> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional
	public Discount save(Discount discount) {
		// TODO Auto-generated method stub
		return repository.saveAndFlush(discount);
	}

	@Override
	public Discount findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Discount> optional = repository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	

}
