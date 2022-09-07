package com.deskover.reponsitory.datatable;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.deskover.entity.Product;

public interface ProductRepoForDatatables extends DataTablesRepository<Product, Long> {
}
