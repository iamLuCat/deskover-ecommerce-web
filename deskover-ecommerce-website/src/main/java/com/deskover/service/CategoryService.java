package com.deskover.service;

import com.deskover.entity.Category;
import org.springframework.data.domain.Page;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
	List<Category> getByActived(Boolean isActive);

	Page<Category> getByActived(Boolean isActive, Integer page, Integer size);

	Category getById(Long id);

	Category update(Category category) throws SQLException;
}
