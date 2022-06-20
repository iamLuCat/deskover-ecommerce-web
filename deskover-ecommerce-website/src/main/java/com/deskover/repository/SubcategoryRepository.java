package com.deskover.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
	List<Subcategory> findByActived(Boolean actived);

	List<Subcategory> findByCategoryId(Long categoryId);
}