package com.deskover.model.entity.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Boolean existsBySlug(String slug);
	Category findBySlug(String slug);
	List<Category> findByActived(Boolean isActive);
	Page<Category> findByActived(Boolean isActive, Pageable pageable);
}