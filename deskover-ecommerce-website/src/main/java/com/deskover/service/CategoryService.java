package com.deskover.service;

import java.sql.SQLException;
import java.util.List;

import com.deskover.entity.Category;

public interface CategoryService {

	List<Category> findByActivated(Boolean isActivated);

	Category findById(Long id);

	Category update(Category category) throws SQLException;
}
