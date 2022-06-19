package com.deskover.service;

import java.sql.SQLException;
import java.util.List;

import com.deskover.entity.Category;

public interface CategoryService {

	List<Category> getByActived(Boolean isActive);

	List<Category> getByActived(Boolean isActive, Integer page, Integer size);

	Category getById(Long id);

	Category update(Category category) throws SQLException;
}
