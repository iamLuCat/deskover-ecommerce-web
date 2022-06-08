package com.deskover.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.entity.Category;
import com.deskover.repository.CategoryRepository;
import com.deskover.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<Category> findAll(Boolean active) {
		return categoryRepository.findByActived(active);
	}

	@Override
	public Category findById(Long id) {
		Optional<Category> optional = categoryRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	@Transactional
	public Category update(Category category) throws SQLException {
		return categoryRepository.saveAndFlush(category);
	}

}
