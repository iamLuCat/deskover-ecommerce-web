package com.deskover.service;

import com.deskover.model.entity.database.FlashSale;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.validation.Valid;

public interface FlashSaleService {
    DataTablesOutput<FlashSale> getByActiveForDatatables(@Valid DataTablesInput input, Boolean isActive);
}
