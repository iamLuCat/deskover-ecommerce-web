package com.deskover.controller.rest.api.ecommerce;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.model.entity.database.Brand;
import com.deskover.model.entity.database.Category;
import com.deskover.model.entity.dto.ecommerce.BrandDTO;
import com.deskover.model.entity.dto.ecommerce.CategoryDTO;
import com.deskover.model.entity.dto.ecommerce.Filter;
import com.deskover.model.entity.dto.ecommerce.ProductDTO;
import com.deskover.model.entity.dto.ecommerce.Shop;
import com.deskover.service.BrandService;
import com.deskover.service.CategoryService;
import com.deskover.service.ShopService;

@RestController
@RequestMapping("api")
public class CustomerAPI {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private ShopService shopService;
	
	@GetMapping("category/all")
	public List<CategoryDTO> getAllCategory(){
		List<CategoryDTO> categoriesModel = new ArrayList<>();
		List<Category> categories = categoryService.getByActived(true);
		categories.stream().forEach(category ->{
			CategoryDTO categoryModel = new CategoryDTO(category);
			categoriesModel.add(categoryModel);
		});		
		return categoriesModel;
	}
	
	@GetMapping("brand/all")
	public List<BrandDTO> getAllBrand(){
		List<BrandDTO> brandsModel = new ArrayList<>();
		List<Brand> brands = brandService.getByActived(true);
		brands.stream().forEach(brand ->{
			BrandDTO brandModel = new BrandDTO(brand);
			brandsModel.add(brandModel);
		});		
		return brandsModel;
	}
	
	@PostMapping("shop/search")
	public Shop search(@RequestBody Filter filter){
		return shopService.search(filter);
	}
	
	@GetMapping("product/item")
	public ProductDTO search(@RequestParam String s){
		return shopService.getProduct(s);
	}
	
}
