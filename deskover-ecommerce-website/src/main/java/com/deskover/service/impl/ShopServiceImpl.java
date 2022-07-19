package com.deskover.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.deskover.dto.api.FilterModel;
import com.deskover.dto.api.ProductModel;
import com.deskover.dto.api.ShopModel;
import com.deskover.entity.Product;
import com.deskover.repository.ProductRepository;
import com.deskover.service.ShopService;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Autowired
	private ProductRepository productRepo;

	@Override
	public ShopModel search(FilterModel filter) {
		String keyword = filter.getKeyword();
		Pageable pageable = PageRequest.of(filter.getCurrentPage(), filter.getItemsPerPage());
		
		int firstResult = filter.getCurrentPage()*filter.getItemsPerPage();
		
		switch (filter.getSort()) {
		case "1":
			pageable = PageRequest.of(filter.getCurrentPage(), filter.getItemsPerPage(), Sort.by("price").ascending());
			break;
		case "2":
			pageable = PageRequest.of(filter.getCurrentPage(), filter.getItemsPerPage(), Sort.by("price").descending());
			break;
		case "3":
			pageable = PageRequest.of(filter.getCurrentPage(), filter.getItemsPerPage(), Sort.by("averageRating").descending());
			break;
		case "4":
			pageable = PageRequest.of(filter.getCurrentPage(), filter.getItemsPerPage(), Sort.by("name").ascending());
			break;
		case "5":
			pageable = PageRequest.of(filter.getCurrentPage(), filter.getItemsPerPage(), Sort.by("name").descending());
			break;
		}
		
		
		
		Page<Product> products = productRepo.searchPage(keyword, 
				filter.getCategory(), 
				filter.getSubcategory(), 
				filter.getMinPrice(), 
				filter.getMaxPrice(), 
				filter.getBrands(),
				pageable);
		return new ShopModel(products);
	}

	@Override
	public ProductModel getProduct(String slug) {
		Product product = productRepo.findBySlug(slug);
		ProductModel data = new ProductModel(product);
		
		return data;
	}
}
