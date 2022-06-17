package com.deskover.repository;

import com.deskover.entity.Category;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	List<Category> findByActived(Boolean isActived);
	List<Category> findByActived(Boolean isActived, Pageable pageable);
}