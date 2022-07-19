package com.deskover.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.deskover.dto.ProductDto;
import com.deskover.entity.Product;

public interface ProductService {

	Page<Product> getByActive(Boolean isActive, Optional<Integer> page, Optional<Integer> size);

	Page<Product> getByName(String name, Optional<Integer> page, Optional<Integer> size);

	List<Product> getBySubcategoryId(Long id);

	Product create(ProductDto productDto);

	Product changeActive(Long id);

	Product update(Product product);

	Product findById(Long id);

	Product findBySlug(String slug);

	Boolean existsBySlug(String slug);

	Boolean existsBySlug(Product product);

	DataTablesOutput<Product> getByActiveForDatatables(@Valid DataTablesInput input, Boolean isActive, Long categoryId);

	void changeDelete(List<Product> products, Boolean isActive);

	void changeActiveSubcategoty(Long id);

}