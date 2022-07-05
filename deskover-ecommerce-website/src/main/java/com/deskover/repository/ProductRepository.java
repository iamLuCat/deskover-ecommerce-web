package com.deskover.repository;

import com.deskover.entity.Product;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByActived(Boolean actived, Pageable Page);
	Boolean existsBySlug(String slug);
	Product findBySlug(String slug);
	List<Product> findBySubCategoryId(Long id);

	// Remo
}