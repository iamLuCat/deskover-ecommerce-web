package com.deskover.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.deskover.model.entity.database.Product;
import com.deskover.model.entity.database.repository.ProductRepository;
import com.deskover.model.entity.dto.ecommerce.Filter;
import com.deskover.model.entity.dto.ecommerce.ProductDTO;
import com.deskover.model.entity.dto.ecommerce.Shop;
import com.deskover.service.ShopService;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Autowired
	private ProductRepository productRepo;

	@Override
	public Shop search(Filter filter) {
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
		
		
		
		return new Shop(products);
	}
	
	@Override
	public ProductDTO getProduct(String slug) {
		Product product = productRepo.findBySlug(slug);
		ProductDTO data = new ProductDTO(product);
		
		return data;
	}
}
