package com.deskover.service;

import java.sql.SQLException;
import java.util.List;

import com.deskover.entity.Category;

public interface CategoryService {

	List<Category> findAll(Boolean active);

	Category findById(Long id);

	Category update(Category category) throws SQLException;
}
