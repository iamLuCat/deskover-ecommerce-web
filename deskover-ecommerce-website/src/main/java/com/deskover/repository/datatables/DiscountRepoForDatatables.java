package com.deskover.repository.datatables;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.deskover.entity.Discount;

public interface DiscountRepoForDatatables extends DataTablesRepository<Discount, Long> {

}
