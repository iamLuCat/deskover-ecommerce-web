package com.deskover.service;

import com.deskover.entity.Brand;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

public interface BrandService {
	List<Brand> getAll();
	List<Brand> getAllBrandIsActived();
	Brand getById(Long id);
	Brand getBySlug(String slug);
	Boolean existsBySlug(String slug);
	Brand create(Brand brand);
	Brand update(Long id,Brand brand);
	void delete(Long id);
	void changeActived(Long id);
	DataTablesOutput<Brand> getAllForDatatables(DataTablesInput input);
}
