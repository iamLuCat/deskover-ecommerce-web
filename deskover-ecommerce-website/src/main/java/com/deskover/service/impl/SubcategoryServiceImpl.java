package com.deskover.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.entity.Subcategory;
import com.deskover.repository.SubcategoryRepository;
import com.deskover.repository.datatables.SubCategoryRepoForDatatables;
import com.deskover.service.CategoryService;
import com.deskover.service.ProductService;
import com.deskover.service.SubcategoryService;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {
	
	@Autowired
	SubcategoryRepository repo;
	
	@Autowired
	SubCategoryRepoForDatatables repoForDatatables;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;

	@Override
	public List<Subcategory> getByCategory(Long categoryId) {
		return repo.findByCategoryId(categoryId);
	}

	public List<Subcategory> getByActive(Boolean isActive) {
		return repo.findByActived(isActive);
	}

	public Subcategory getById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Subcategory update(Subcategory subcategory) {
		subcategory.setModifiedAt(new Timestamp(System.currentTimeMillis()));
		return repo.saveAndFlush(subcategory);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Subcategory subcategory = this.getById(id);
		if (subcategory == null) {
			throw new IllegalArgumentException("Subcategory not found");
		}

		subcategory.setDeletedAt(new Timestamp(System.currentTimeMillis()));
		subcategory.setActived(Boolean.FALSE);

		Subcategory result = repo.save(subcategory);
		if (result.getActived() == Boolean.TRUE) {
			throw new IllegalArgumentException("Subcategory not deleted");
		}
	}

	@Override
	public void deleteMultiple(List<Subcategory> subcategories) {
		subcategories.forEach(subcategory -> {
			subcategory.setDeletedAt(new Timestamp(System.currentTimeMillis()));
			subcategory.setActived(Boolean.FALSE);
		});
		repo.saveAll(subcategories);
	}

	@Override
	public DataTablesOutput<Subcategory> getAllForDatatables(DataTablesInput input) {
		
		 DataTablesOutput<Subcategory> subcategories = repoForDatatables.findAll(input);
	        if (subcategories.getError() != null) {
	            throw new IllegalArgumentException(subcategories.getError());
	        }
		 
		return subcategories;
	}

	@Override
	public Boolean existsBySlug(String slug) {
		Subcategory subcategory = repo.findBySlug(slug);
		return subcategory!=null;
	}

	@Override
	public Subcategory create(Subcategory subcategory) {
		subcategory.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		return repo.saveAndFlush(subcategory);
	}

	@Override
	public Boolean existsBySlug(Subcategory subcategory) {
		Subcategory subcategoryExists = repo.findBySlug(subcategory.getSlug());
		Boolean isExits =(subcategoryExists!=null && !subcategoryExists.getId().equals(subcategory.getId())) || productService.existsBySlug(subcategory.getSlug())
				|| categoryService.existsBySlug(subcategory.getSlug());
		System.out.println(isExits);

		return isExits;
	}

	@Override
	@Transactional
	public void deleteAll(List<Subcategory> subcategories) {
		subcategories.forEach(subcategory -> {
			repo.delete(subcategory);
		});
		
		
	}

}
