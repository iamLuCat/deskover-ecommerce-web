package com.deskover.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
	List<Brand> findByActived(Boolean actived);
	Brand findBySlug(String slug);
	Boolean existsBySlug(String slug);
}