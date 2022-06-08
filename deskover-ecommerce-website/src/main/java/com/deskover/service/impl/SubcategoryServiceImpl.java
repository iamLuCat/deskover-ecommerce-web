package com.deskover.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.entity.Category;
import com.deskover.entity.Subcategory;
import com.deskover.repository.SubcategoryRepository;
import com.deskover.service.SubcategoryService;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {
	
	@Autowired
	SubcategoryRepository subcategoryRepository;

	@Override
	public List<Subcategory> findAllActived(Boolean active) {
		return subcategoryRepository.findByActived(active);
	}

	@Override
	public Subcategory findById(Long id) {
		Optional<Subcategory> optional = subcategoryRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	@Transactional
	public Subcategory update(Subcategory subcategory) throws SQLException {
		return subcategoryRepository.saveAndFlush(subcategory);
	}

}
