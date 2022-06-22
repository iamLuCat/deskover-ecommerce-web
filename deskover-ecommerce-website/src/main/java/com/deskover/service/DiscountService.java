package com.deskover.service;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.deskover.entity.Discount;

public interface DiscountService {
	
	List<Discount> findAll();
	
	Discount create(Discount discount);
	
	Discount changeActive(Long id);
	
	Discount update(Discount discount);

	Discount findById(Long id);
	
	DataTablesOutput<Discount> getAllForDatatables(DataTablesInput input);

}
