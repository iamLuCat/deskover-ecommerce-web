package com.deskover.service;

import java.util.List;

import com.deskover.entity.Discount;

public interface DiscountService {
	
	List<Discount> findAll();
	
	Discount save(Discount discount);

	Discount findById(Long id);

}
