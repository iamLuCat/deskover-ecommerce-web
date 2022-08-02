package com.deskover.service.impl;

import com.deskover.model.entity.database.FlashSale;
import com.deskover.model.entity.database.repository.FlashSaleRepository;
import com.deskover.model.entity.database.repository.datatable.FlashSaleRepoForDatatables;
import com.deskover.service.FlashSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class FlashSaleServiceImpl implements FlashSaleService {
    @Autowired
    private FlashSaleRepository repository;

    @Autowired
    private FlashSaleRepoForDatatables repoForDatatables;

    @Override
    public DataTablesOutput<FlashSale> getByActiveForDatatables(@Valid DataTablesInput input, Boolean isActive) {
        DataTablesOutput<FlashSale> discount = repoForDatatables.findAll(input,
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("actived"), isActive));
        if (discount.getError() != null) {
            throw new IllegalArgumentException(discount.getError());
        }
        return discount;
    }
}
