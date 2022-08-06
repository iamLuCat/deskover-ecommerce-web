package com.deskover.controller.rest.api.ecommerce;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.model.entity.database.Brand;
import com.deskover.model.entity.database.Category;
import com.deskover.model.entity.database.Users;
import com.deskover.model.entity.dto.ecommerce.BrandDTO;
import com.deskover.model.entity.dto.ecommerce.CategoryDTO;
import com.deskover.model.entity.dto.ecommerce.Filter;
import com.deskover.model.entity.dto.ecommerce.FormReview;
import com.deskover.model.entity.dto.ecommerce.ProductDTO;
import com.deskover.model.entity.dto.ecommerce.Reviewer;
import com.deskover.model.entity.dto.ecommerce.Shop;
import com.deskover.service.BrandService;
import com.deskover.service.CategoryService;
import com.deskover.service.RatingService;
import com.deskover.service.ShopService;
import com.deskover.service.UserService;

@RestController
@RequestMapping("/api/v1/ecommerce/")
public class CustomerAPI {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RatingService ratingService;
	
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
	
	@GetMapping("product/review")
	public Reviewer getReviewer(@RequestParam Integer c, @RequestParam String s){
		return shopService.getReviewer(s, c);
	}
	
	@PostMapping("product/review")
	public void postReviewer(@RequestBody FormReview f, Principal principal){
		
		try {
			Users user = userService.findByUsername(principal.getName());
			f.setName(user.getFullname());
			f.setEmail(user.getEmail());
		} catch (Exception e) {}
		
		ratingService.postReview(f);
	}
	
	@PostMapping("product/wishlist")
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public void addWishlist(@RequestBody FormReview f, Principal principal){
		Users user = userService.findByUsername(principal.getName());
		f.setName(user.getFullname());
		f.setEmail(user.getEmail());
		
		ratingService.postReview(f);
	}
	
}
