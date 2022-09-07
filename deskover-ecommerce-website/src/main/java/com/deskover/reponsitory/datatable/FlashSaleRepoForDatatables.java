package com.deskover.reponsitory.datatable;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.deskover.entity.FlashSale;

public interface FlashSaleRepoForDatatables extends DataTablesRepository<FlashSale, Long> {

}
