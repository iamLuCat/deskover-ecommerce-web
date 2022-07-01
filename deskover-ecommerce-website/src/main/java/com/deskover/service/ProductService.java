package com.deskover.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.deskover.dto.ProductDto;
import com.deskover.entity.Product;

public interface ProductService{
	
	List<Product> findByActived(Boolean actived,Integer page, Integer size);
	
	List<Product> findBySubcategoryId(Long id);
	
	Product create(ProductDto productDto);
	
	Product changeActive(Long id);
	
	Product update(Product product);

	Product findById(Long id);
	
	Product findBySlug(String slug);

	Boolean existsBySlug(String slug);

	Boolean existsBySlug(Product product);

	DataTablesOutput<Product> getByActiveForDatatables(@Valid DataTablesInput input, Boolean isActive);
	
	void changeDelete(List<Product> products,Boolean isActive);
	
	void changeActiveSubcategoty(Long id);




}