package com.deskover.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.entity.Category;
import com.deskover.repository.CategoryRepository;
import com.deskover.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepository repo;

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	@Transactional
	public Category save(Category category) throws SQLException {
		// TODO Auto-generated method stub
		return repo.saveAndFlush(category) ;
	}



}
