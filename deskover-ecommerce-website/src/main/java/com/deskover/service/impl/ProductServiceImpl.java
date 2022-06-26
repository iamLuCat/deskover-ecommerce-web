package com.deskover.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.dto.ProductDto;
import com.deskover.entity.Product;
import com.deskover.repository.ProductRepository;
import com.deskover.repository.datatables.ProductRepoForDatatables;
import com.deskover.service.BrandService;
import com.deskover.service.CategoryService;
import com.deskover.service.DiscountService;
import com.deskover.service.ProductService;
import com.deskover.service.SubcategoryService;
import com.deskover.util.MapperUtil;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private ProductRepoForDatatables repoForDatatables;

	@Autowired
	private SubcategoryService subcategoryService;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private DiscountService discountService;

	@Override
	public List<Product> findByActived(Boolean actived, Integer page, Integer size) {
		if (page > 0) {
			Pageable pageable = PageRequest.of(page, size);
			return repository.findByActived(actived, pageable);
		} else {
			Pageable pageable = PageRequest.of(0, size);
			return repository.findByActived(actived, pageable);
		}
	}

	@Override
	@Transactional
	public Product create(ProductDto productDto) {
		Product product = MapperUtil.map(productDto, Product.class);
		if(this.existsBySlug(product)) {
			Product productExists = repository.findBySlug(product.getSlug());
			if(productExists != null && !productExists.getActived()) {
				productExists.setActived(Boolean.TRUE);
				productExists.setName(product.getName());
				productExists.setDescription(product.getDescription());
				productExists.setPrice(product.getPrice());
				productExists.setImage(product.getImage());
				productExists.setModifiedDate(new Timestamp(System.currentTimeMillis()));
				productExists.setModifiedUser(SecurityContextHolder.getContext().getAuthentication().getName());
				productExists.setSubCategory(subcategoryService.getById(productDto.getSubcategogyId()));
				productExists.setBrand(brandService.getById(productDto.getBrandId()));
				if(productDto.getDiscountId() != null && !productDto.getDiscountId().equals("")) {
					productExists.setDiscount(discountService.findById(productDto.getDiscountId()));
				}
				productExists.setDiscount(null);
				return this.update(productExists);
			}else {
	            throw new IllegalArgumentException("Slug đã tồn tại");
			}
		}else {
			product.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			product.setModifiedUser(SecurityContextHolder.getContext().getAuthentication().getName());
			product.setSubCategory(subcategoryService.getById(productDto.getSubcategogyId()));
			product.setBrand(brandService.getById(productDto.getBrandId()));
			if(productDto.getDiscountId() != null && !productDto.getDiscountId().equals("")) {
				product.setDiscount(discountService.findById(productDto.getDiscountId()));
			}
			product.setDiscount(null);
			return repository.saveAndFlush(product);
		}
	}
	
	@Override
	@Transactional
	public Product changeActive(Long id) {
		Product product = this.getById(id);
		if(product == null ) {
			throw new IllegalArgumentException("Không tìm thấy sản phẩm");
		}
		if(product.getActived()) {
			product.setModifiedDate(new Timestamp(System.currentTimeMillis()));
			product.setActived(Boolean.FALSE);
			product.setModifiedUser(SecurityContextHolder.getContext().getAuthentication().getName());
			return repository.saveAndFlush(product);
		}else {
			product.setModifiedDate(new Timestamp(System.currentTimeMillis()));
			product.setActived(Boolean.FALSE);
			product.setModifiedUser(SecurityContextHolder.getContext().getAuthentication().getName());
			return repository.saveAndFlush(product);
		}

	}

	@Override
	@Transactional
	public Product update(Product product) {
		product.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		product.setModifiedUser(SecurityContextHolder.getContext().getAuthentication().getName());
		return repository.saveAndFlush(product);
	}

	@Override
	public Product findById(Long id) {
		Optional<Product> optional = repository.findById(id);
		return optional.orElse(null);
	}

	@Override
	public DataTablesOutput<Product> getAllForDatatables(DataTablesInput input) {
		DataTablesOutput<Product> Product = repoForDatatables.findAll(input);
		if (Product.getError() != null) {
			throw new IllegalArgumentException(Product.getError());
		}
		return Product;
	}

	public Product getById(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Boolean existsBySlug(String slug) {
		Product product = repository.findBySlug(slug);
		return product != null;
	}

	@Override
	public Product findBySlug(String slug) {
		return repository.findBySlug(slug);
	}

	@Override
	public Boolean existsBySlug(Product product) {
		Product productExits = repository.findBySlug(product.getSlug());
		Boolean isExits = (productExits != null && !productExits.getId().equals(product.getId()))
				|| subcategoryService.existsBySlug(product.getSlug())
				|| categoryService.existsBySlug(product.getSlug());
		System.out.println(isExits);

		return isExits;
	}

	@Override
	public DataTablesOutput<Product> getByActiveForDatatables(@Valid DataTablesInput input, Boolean isActive) {
		DataTablesOutput<Product> products = repoForDatatables.findAll(input,(root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("actived"), isActive));
		if (products.getError() != null) {
			throw new IllegalArgumentException(products.getError());
		}
		return products;
	}



}