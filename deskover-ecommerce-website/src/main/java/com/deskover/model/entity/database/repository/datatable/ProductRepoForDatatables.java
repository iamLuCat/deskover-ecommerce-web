package com.deskover.model.entity.database.repository.datatable;

import com.deskover.model.entity.database.Product;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface ProductRepoForDatatables extends DataTablesRepository<Product, Long> {
}
