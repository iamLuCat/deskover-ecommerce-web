package com.deskover.service;

import com.deskover.entity.Brand;
import com.deskover.entity.Category;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

public interface BrandService {
	List<Brand> getAll();
	List<Brand> getAllBrandIsActived();
	Brand getById(Long id);
	Brand getBySlug(String slug);
	Boolean existsBySlug(String slug);
	Boolean existsBySlug(Brand brand);
	Brand create(Brand brand);
	Brand update(Brand brand);
	void delete(Long id);
	void changeActived(Long id);
	DataTablesOutput<Brand> getByActiveForDatatables(DataTablesInput input, Boolean isActive);
	List<Brand> getByActived(Boolean isActive);
}
