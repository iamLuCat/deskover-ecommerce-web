package com.deskover.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.entity.Category;
import com.deskover.repository.CategoryRepository;
import com.deskover.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;

	/**
	 * Find categories by activated
	 * @param isActived - true or false
	 * @return list of categories by activated
	 */
	@Override
	public List<Category> getByActived(Boolean isActived) {
		return categoryRepository.findByActived(isActived);
	}

	@Override
	public List<Category> getByActived(Boolean isActived, Integer page, Integer size) {
		return categoryRepository.findByActived(isActived, PageRequest.of(page, size));
	}

	/**
	 * Find category by id
	 * @param id - category id
	 * @return	category by id
	 */
	@Override
	public Category getById(Long id) {
		return categoryRepository.findById(id).orElse(null);
	}

	/**
	 * Update category
	 * @param category - category to update
	 * @return category updated
	 * @throws SQLException - if error occurs
	 */
	@Override
	@Transactional
	public Category update(Category category) throws SQLException {
		return categoryRepository.saveAndFlush(category);
	}

}
