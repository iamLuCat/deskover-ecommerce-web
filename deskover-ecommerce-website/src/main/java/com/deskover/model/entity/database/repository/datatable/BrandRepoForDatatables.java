package com.deskover.model.entity.database.repository.datatable;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.deskover.model.entity.database.Brand;

public interface BrandRepoForDatatables extends DataTablesRepository<Brand, Long> {
}
