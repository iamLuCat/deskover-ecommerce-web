package com.deskover.service;

import javax.validation.Valid;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.deskover.model.entity.database.FlashSale;
public interface FlashSaleService {
    DataTablesOutput<FlashSale> getByActiveForDatatables(@Valid DataTablesInput input, Boolean isActive);

	void isCheckActived();
	
	FlashSale create(FlashSale flashSale);
	
	void delete(Long id);
	
	FlashSale updateProductToFlashSale(FlashSale discount, Long productIdToAdd, Long productIdToRemove);
	
	FlashSale getById(Long id);
	
	FlashSale getFlashSale();
	
}
