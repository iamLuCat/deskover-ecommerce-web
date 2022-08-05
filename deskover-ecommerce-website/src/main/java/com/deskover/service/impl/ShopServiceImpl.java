package com.deskover.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.deskover.model.entity.database.Brand;
import com.deskover.model.entity.database.FlashSale;
import com.deskover.model.entity.database.Product;
import com.deskover.model.entity.database.Rating;
import com.deskover.model.entity.database.repository.BrandRepository;
import com.deskover.model.entity.database.repository.FlashSaleRepository;
import com.deskover.model.entity.database.repository.ProductRepository;
import com.deskover.model.entity.database.repository.RatingRepository;
import com.deskover.model.entity.dto.ecommerce.BrandDTO;
import com.deskover.model.entity.dto.ecommerce.Filter;
import com.deskover.model.entity.dto.ecommerce.FlashSaleDTO;
import com.deskover.model.entity.dto.ecommerce.Item;
import com.deskover.model.entity.dto.ecommerce.ProductDTO;
import com.deskover.model.entity.dto.ecommerce.Reviewer;
import com.deskover.model.entity.dto.ecommerce.Shop;
import com.deskover.service.ShopService;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private FlashSaleRepository flashSaleRepo;

	@Autowired
	private BrandRepository brandRepo;
	
	@Autowired
	private RatingRepository ratingRepo;
	
	@Override
	public Shop search(Filter filter) {
		String keyword = filter.getKeyword();
		Pageable pageable = PageRequest.of(filter.getCurrentPage(), filter.getItemsPerPage());
		
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

	@Override
	public List<Item> getRecommendList(Long category) {
		Page<Product> items = productRepo.getProductBasedOnCategoryID(category, PageRequest.of(0,20));
		
		return items.toList().stream().map(product -> new Item(product)).collect(Collectors.toList());
	}

	@Override
	public List<Item> get4TopRate() {
		Page<Product> items = productRepo.getProduct(PageRequest.of(0, 4, Sort.by("averageRating").descending()));
		
		return items.toList().stream().map(product -> new Item(product)).collect(Collectors.toList());
	}

	@Override
	public List<Item> get4TopSale() {
		Page<Product> items = productRepo.getProduct(PageRequest.of(0, 4, Sort.by("priceSale").descending()));
		
		return items.toList().stream().map(product -> new Item(product)).collect(Collectors.toList());
	}

	@Override
	public List<Item> get4TopSold() {
		Page<Product> items = productRepo.getProduct(PageRequest.of(0, 4, Sort.by("totalSold").descending()));
		
		return items.toList().stream().map(product -> new Item(product)).collect(Collectors.toList());
	}

	@Override
	public FlashSaleDTO getFlashSale() {
		FlashSale fs = flashSaleRepo.findFirstByActived(true);
		List<Product> products = productRepo.findByFlashSale(fs);
		FlashSaleDTO flashsale = new FlashSaleDTO(fs);
		flashsale.setItems(products.stream().map(product -> new Item(product)).collect(Collectors.toList()));
		
		return new FlashSaleDTO(fs);
	}

	@Override
	public List<BrandDTO> getListBrand() {
		List<Brand> b = brandRepo.findByActived(true);
		
		return b.stream().map(brand -> new BrandDTO(brand)).collect(Collectors.toList());
	}
	
	@Override
	public Reviewer getReviewer(String slug, Integer page) {
		Page<Rating> ratings = ratingRepo.getRating(slug, PageRequest.of(page, 4, Sort.by("modifiedAt").descending()));
		return new Reviewer(ratings);
	}
}
