package com.deskover.service;

import java.sql.SQLException;
import java.util.List;

import com.deskover.entity.Category;

public interface CategoryService {
	List<Category> findAll();
	Category save(Category category) throws SQLException;
	

}
