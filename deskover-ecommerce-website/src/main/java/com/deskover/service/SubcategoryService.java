package com.deskover.service;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.deskover.entity.Subcategory;

public interface SubcategoryService {

	List<Subcategory> getByCategory(Long categoryId);
	
	List<Subcategory> getByActive(Boolean isActive);

    List<Subcategory> getAll(Boolean isActive, Long categoryId);

    Subcategory getById(Long id);

    DataTablesOutput<Subcategory> getByActiveForDatatables(DataTablesInput input, Boolean isActive);

	DataTablesOutput<Subcategory> getByActiveForDatatables(DataTablesInput input, Boolean isActive, Long categoryId);

    Subcategory create(Subcategory subcategory);

	Subcategory update(Subcategory subcategory);

	void delete(Long id);

	void deleteMultiple(List<Subcategory> subcategories);

	DataTablesOutput<Subcategory> getAllForDatatables(DataTablesInput input);

	Boolean existsBySlug(String slug);
	
	Boolean existsByOtherSlug(Subcategory subcategory);

	Boolean existsBySlug(Subcategory subcategory);

	void deleteAll(List<Subcategory> subcategories);

	Subcategory changeActive(Long id);
}
