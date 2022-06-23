package com.deskover.service;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.deskover.entity.Product;

public interface ProductService{
	List<Product> findByActived(Boolean actived,Integer page, Integer size);
	
	Product create(Product product);
	
	void delete(Long id);
	
	Product update(Product product);

	Product findById(Long id);
	
	Product findBySlug(String slug);
	
	DataTablesOutput<Product> getAllForDatatables(DataTablesInput input);

	Boolean existsBySlug(String slug);

	Boolean existsBySlug(Product product);
}