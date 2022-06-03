package com.deskover.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.entity.Category;
import com.deskover.repository.CategoryRepository;
import com.deskover.service.CategoryServiceHai;

@Service
public class CategoryServiceImplHai implements CategoryServiceHai {
	
	@Autowired
	CategoryRepository repo;

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Category findById(Long id) {
		// TODO Auto-generated method stub
		return repo.getById(id);
	}

	@Override
	public Category findBySlug(String slug) {
		// TODO Auto-generated method stub
		return repo.findBySlug(slug);
	}

	@Override
	public void create(Category category) {
		// TODO Auto-generated method stub
		try {
			if(repo.existsBySlug(category.getSlug())) {
				throw new RuntimeException("Slug này đã tồn tại");
			}
			repo.save(category);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Category category = repo.getById(id);
		category.setEnabled(Boolean.FALSE);
		repo.saveAndFlush(category);
	}

	@Override
	public void update(Category category) {
		// TODO Auto-generated method stub
		try {
			if(repo.existsBySlug(category.getSlug())) {
				throw new RuntimeException("Slug này đã tồn tại");
			}
			repo.saveAndFlush(category);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
}
