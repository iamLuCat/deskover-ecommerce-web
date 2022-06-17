package com.deskover.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deskover.entity.Product;
import com.deskover.repository.ProductRepository;
import com.deskover.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> findByActived(Boolean actived, Integer p, Integer page) {
		if(p>0) {
			Pageable pageable = PageRequest.of(p, page);
			return productRepository.findByActived(actived, pageable);
		}else {
			Pageable pageable = PageRequest.of(0, page);
			return productRepository.findByActived(actived, pageable);
		}	
	}


	
}