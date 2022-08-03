package com.deskover.service;

import javax.validation.Valid;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.deskover.model.entity.database.FlashSale;
import com.deskover.model.entity.dto.FlashSaleDto;
public interface FlashSaleService {
    DataTablesOutput<FlashSale> getByActiveForDatatables(@Valid DataTablesInput input, Boolean isActive);

	void isCheckActived();
	
	FlashSale create(FlashSale flashSale);
	
	FlashSale getById(Long id);
	
	FlashSale getFlashSale();
}
