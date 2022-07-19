package com.deskover.service;

import java.util.List;

import com.deskover.dto.SubcategoryDto;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.entity.Subcategory;

public interface SubcategoryService {

	List<Subcategory> getByCategory(Long categoryId);
	
	List<Subcategory> getByActive(Boolean isActive);

    List<Subcategory> getAll(Boolean isActive, Long categoryId);

    Subcategory getById(Long id);

    DataTablesOutput<Subcategory> getByActiveForDatatables(DataTablesInput input, Boolean isActive);

	DataTablesOutput<Subcategory> getByActiveForDatatables(DataTablesInput input, Boolean isActive, Long categoryId);

	Subcategory create(SubcategoryDto subcategoryDto);
    
//    Subcategory create(Subcategory subcategory);

	Subcategory update(SubcategoryDto subcategoryDto);

	@Transactional
	Subcategory update(Subcategory subcategory);

	@Transactional
	void delete(Long id);

	void deleteMultiple(List<Subcategory> subcategories);

	DataTablesOutput<Subcategory> getAllForDatatables(DataTablesInput input);

	Boolean existsBySlug(String slug);
	
	Boolean existsBySlug(Subcategory subcategory);

	void deleteAll(List<Subcategory> subcategories);

	Subcategory changeActive(Long id);
}
