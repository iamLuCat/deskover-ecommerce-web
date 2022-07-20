package com.deskover.service;

import com.deskover.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ProductService {

	Page<Product> getByActive(Boolean isActive, Optional<Integer> page, Optional<Integer> size);

	Page<Product> getByName(String name, Optional<Integer> page, Optional<Integer> size);

	List<Product> getBySubcategoryId(Long id);

	Product create(Product product);

	Product changeActive(Long id);

	Product save(Product product);

	Product findById(Long id);

	Boolean existsBySlug(String slug);

	Boolean existsByOtherSlug(Product product);

    Boolean existsBySlug(Product product);

    DataTablesOutput<Product> getByActiveForDatatables(
			@Valid DataTablesInput input,
			Boolean isActive,
			Long categoryId,
			Long brandId,
			Boolean isDiscount
	);

	void changeDelete(List<Product> products, Boolean isActive);

	void changeActiveSubcategoty(Long id);

	List<Product> getProductByCreateAtDesc(Boolean active);

}