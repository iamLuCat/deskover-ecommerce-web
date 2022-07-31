package com.deskover.model.entity.database.repository.datatable;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.deskover.model.entity.database.Product;

public interface ProductRepoForDatatables extends DataTablesRepository<Product, Long> {

}
