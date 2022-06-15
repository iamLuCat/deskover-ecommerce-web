package com.deskover.service;

import java.util.List;

import com.deskover.entity.Brand;

public interface BrandService {
	List<Brand> getAll();
	List<Brand> getAllBrandIsActived();
	Brand get(Long id);
	Brand get(String slug);
	Boolean existsBySlug(String slug);
	Brand create(Brand brand);
	Brand update(Long id,Brand brand);
	void delete(Long id);
}
