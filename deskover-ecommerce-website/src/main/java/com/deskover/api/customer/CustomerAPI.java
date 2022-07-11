package com.deskover.api.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.entity.Brand;
import com.deskover.entity.Category;
import com.deskover.entity.api.BrandModel;
import com.deskover.entity.api.CategoryModel;
import com.deskover.service.BrandService;
import com.deskover.service.CategoryService;

@RestController
@RequestMapping("api")
public class CustomerAPI {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private BrandService brandService;
	
	@GetMapping("category/all")
	public List<CategoryModel> getAllCategory(){
		List<CategoryModel> categoriesModel = new ArrayList<>();
		List<Category> categories = categoryService.getByActived(true);
		categories.stream().forEach(category ->{
			CategoryModel categoryModel = new CategoryModel(category);
			categoriesModel.add(categoryModel);
		});		
		return categoriesModel;
	}
	
	@GetMapping("brand/all")
	public List<BrandModel> getAllBrand(){
		List<BrandModel> brandsModel = new ArrayList<>();
		List<Brand> brands = brandService.getByActived(true);
		brands.stream().forEach(brand ->{
			BrandModel brandModel = new BrandModel(brand);
			brandsModel.add(brandModel);
		});		
		return brandsModel;
	}
	
}
