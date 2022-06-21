package com.deskover.service;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.entity.Subcategory;
import org.springframework.transaction.annotation.Transactional;

public interface SubcategoryService {

	List<Subcategory> getByCategory(Long categoryId);
	
	List<Subcategory> getByActive(Boolean isActive);

	Subcategory getById(Long id);
	
	Subcategory create(Subcategory subcategory);

	Subcategory update(Subcategory subcategory);

	@Transactional
	void delete(Long id);

	void deleteMultiple(List<Subcategory> subcategories);

	DataTablesOutput<Subcategory> getAllForDatatables(DataTablesInput input);

	Boolean existsBySlug(String slug);
	
	Boolean existsBySlug(Subcategory subcategory);

	void deleteAll(List<Subcategory> subcategories);
}
