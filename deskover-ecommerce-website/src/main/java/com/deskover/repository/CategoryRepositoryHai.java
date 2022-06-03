package com.deskover.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.Category;

public interface CategoryRepositoryHai extends JpaRepository<Category, Long> {
	List<Category> findAll();
	Category findBySlug(String slug);
	Boolean existsBySlug(String slug);
}