package com.deskover.repository.datatables;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.deskover.entity.Subcategory;

public interface SubCategoryRepoForDatatables extends DataTablesRepository<Subcategory, Long> { }
