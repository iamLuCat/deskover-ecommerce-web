package com.deskover.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.entity.Product;
import com.deskover.repository.ProductRepository;
import com.deskover.repository.datatables.ProductRepoForDatatables;
import com.deskover.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private ProductRepoForDatatables repoForDatatables;

	@Override
	public List<Product> findByActived(Boolean actived, Integer page, Integer size) {
		if(page>0) {
			Pageable pageable = PageRequest.of(page, size);
			return repository.findByActived(actived, pageable);
		}else {
			Pageable pageable = PageRequest.of(0, size);
			return repository.findByActived(actived, pageable);
		}	
	}

	@Override
	@Transactional
	public Product create(Product product) {
		product.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		return repository.saveAndFlush(product);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		Product product = this.getById(id);
		product.setDeletedDate(new Timestamp(System.currentTimeMillis()));
		product.setActived(Boolean.FALSE);
		repository.saveAndFlush(product);
	}

	@Override
	@Transactional
	public Product update(Product product) {
		product.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		return repository.saveAndFlush(product);
	}

	@Override
	public Product findById(Long id) {
		Optional<Product> optional = repository.findById(id);
		return optional.isPresent() ? optional.get() : null;
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
		return product!=null;
	}

	@Override
	public Product findBySlug(String slug) {
		// TODO Auto-generated method stub
		return repository.findBySlug(slug);
	}

}