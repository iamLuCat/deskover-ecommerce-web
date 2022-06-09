package com.deskover.service;

import java.sql.SQLException;
import java.util.List;

import com.deskover.entity.Subcategory;

public interface SubcategoryService {
	
	List<Subcategory> findByActivated(Boolean isActivated);

	Subcategory findById(Long id);

	Subcategory update(Subcategory subcategory) throws SQLException;
}
