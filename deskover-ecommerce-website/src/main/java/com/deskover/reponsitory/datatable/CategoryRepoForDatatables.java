package com.deskover.reponsitory.datatable;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.deskover.entity.Category;

public interface CategoryRepoForDatatables extends DataTablesRepository<Category, Long> {
}
