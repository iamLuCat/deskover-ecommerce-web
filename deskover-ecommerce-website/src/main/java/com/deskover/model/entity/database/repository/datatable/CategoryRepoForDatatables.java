package com.deskover.model.entity.database.repository.datatable;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.deskover.model.entity.database.Category;

public interface CategoryRepoForDatatables extends DataTablesRepository<Category, Long> {
}
