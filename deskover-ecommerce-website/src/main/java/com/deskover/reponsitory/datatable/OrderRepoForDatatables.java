package com.deskover.reponsitory.datatable;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.deskover.entity.Order;

public interface OrderRepoForDatatables extends DataTablesRepository<Order, Long> {
}
