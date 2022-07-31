package com.deskover.service;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.deskover.model.entity.database.Brand;

public interface BrandService {
	List<Brand> getAll();
	List<Brand> getAllBrandIsActived();
	Brand getById(Long id);
	Brand getBySlug(String slug);
	Boolean existsBySlug(String slug);
	Boolean existsByOtherSlug(Brand brand);
	Brand create(Brand brand);
	Brand update(Brand brand);
	void delete(Long id);
	void changeActived(Long id);
	DataTablesOutput<Brand> getByActiveForDatatables(DataTablesInput input, Boolean isActive);
	List<Brand> getByActived(Boolean isActive);
}
