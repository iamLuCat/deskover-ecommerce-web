package com.deskover.service;

import java.util.List;

import com.deskover.entity.Subcategory;
import org.springframework.transaction.annotation.Transactional;

public interface SubcategoryService {

	List<Subcategory> getByCategory(Long categoryId);
	
	List<Subcategory> getByActive(Boolean isActive);

	Subcategory getById(Long id);

	Subcategory update(Subcategory subcategory);

	@Transactional
	void delete(Long id);

	void deleteMultiple(List<Subcategory> subcategories);
}
