package com.deskover.repository.datatables;

import com.deskover.dto.SubcategoryDto;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.deskover.entity.Subcategory;

public interface SubCategoryRepoForDatatables extends DataTablesRepository<Subcategory, Long> { }
