package com.deskover.repository.datatables;

import com.deskover.entity.Order;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface OrderRepoForDatatables extends DataTablesRepository<Order, Long> {
}
