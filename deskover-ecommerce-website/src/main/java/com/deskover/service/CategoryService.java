package com.deskover.service;

import com.deskover.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryService {
	// Check if the slug is already in use by another category
	Boolean existsBySlug(String slug);
	
	Boolean existsBySlug(Category category);

	List<Category> getByActived(Boolean isActive);

    DataTablesOutput<Category> getAllForDatatables(DataTablesInput input);

    Page<Category> getByActived(Boolean isActive, Integer page, Integer size);

	Category getById(Long id);

	@Transactional
	Category create(Category category);

	Category update(Category category);

	void delete(Long id);
}
