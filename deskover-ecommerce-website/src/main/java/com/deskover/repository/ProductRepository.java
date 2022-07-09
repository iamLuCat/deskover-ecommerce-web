package com.deskover.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	Page<Product> findByActived(Boolean actived, Pageable Page);
	
	//Like Name
	Page<Product> findByNameContaining(String name, Pageable Page);
	
	//Like SubCategory
	Page<Product> findBySubCategoryNameContaining(String name, Pageable Page);
	
	//Like Category
	Page<Product> findBySubCategoryCategoryNameContaining(String name, Pageable Page);
	
	Boolean existsBySlug(String slug);
	
	Product findBySlug(String slug);
	
	List<Product> findBySubCategoryId(Long id);

	// Remo
}