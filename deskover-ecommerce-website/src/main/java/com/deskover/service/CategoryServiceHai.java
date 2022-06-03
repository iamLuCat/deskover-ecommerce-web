package com.deskover.service;

import java.util.List;

import com.deskover.entity.Category;

public interface CategoryServiceHai {
	List<Category> findAll();
	Category findById(Long id);
	Category findBySlug(String slug);
	void create(Category category);
	void delete(Long id);
	void update(Category category);
}
