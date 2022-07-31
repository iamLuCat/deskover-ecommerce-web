package com.deskover.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.deskover.model.entity.database.Discount;

public interface DiscountService {
	
	List<Discount> findAll();
	
	Discount create(Discount discount);
	
	Discount changeActive(Long id);
	
	Discount update(Discount discount, Long productIdToAdd, Long productIdToRemove);

	Discount findById(Long id);

	DataTablesOutput<Discount> getByActiveForDatatables(@Valid DataTablesInput input, Boolean orElse);

}
