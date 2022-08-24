package com.deskover.controller.rest.api.ecommerce;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.model.entity.database.Brand;
import com.deskover.model.entity.database.Category;
import com.deskover.model.entity.database.Users;
import com.deskover.model.entity.dto.ecommerce.AccountDTO;
import com.deskover.model.entity.dto.ecommerce.AccountFormDTO;
import com.deskover.model.entity.dto.ecommerce.BrandDTO;
import com.deskover.model.entity.dto.ecommerce.CartLocal;
import com.deskover.model.entity.dto.ecommerce.CategoryDTO;
import com.deskover.model.entity.dto.ecommerce.Filter;
import com.deskover.model.entity.dto.ecommerce.FormReview;
import com.deskover.model.entity.dto.ecommerce.OrderPage;
import com.deskover.model.entity.dto.ecommerce.PasswordDTO;
import com.deskover.model.entity.dto.ecommerce.ProductDTO;
import com.deskover.model.entity.dto.ecommerce.Reviewer;
import com.deskover.model.entity.dto.ecommerce.Shop;
import com.deskover.model.entity.dto.ecommerce.WishlistDTO;
import com.deskover.model.entity.dto.security.payload.MessageResponse;
import com.deskover.service.BrandService;
import com.deskover.service.CategoryService;
import com.deskover.service.RatingService;
import com.deskover.service.ShopService;
import com.deskover.service.UserPasswordService;
import com.deskover.service.UserService;
import com.deskover.service.WishlistService;
import com.deskover.service.impl.CartDTO;

@RestController
@RequestMapping("/api/v1/ecommerce/")
public class EcommerceRestController {
	
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
	
	@Autowired
	private WishlistService wishlistService;
	
	@Autowired
	private UserPasswordService userPassService;
	
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
	public List<String> postWishlist(@RequestBody String p, Principal principal){
		try {
			return wishlistService.setWishlist(p, principal.getName());
		} catch (Exception e) {
			return null;
		}
	}
	
	@GetMapping("product/wishlist")
	public List<String> getWishlist(Principal principal){
		try {
			return wishlistService.getWishlist(principal.getName());
		} catch (Exception e) {
			return null;
		}
	}
	
	@GetMapping("product/wishlist/page")
	public WishlistDTO getWishlist(@RequestParam Integer c, Principal principal){
		try {
			return wishlistService.getWishlist(principal.getName(), c);
		} catch (Exception e) {
			return null;
		}
	}
	
	@PostMapping("user/cart")
	public List<CartDTO> getCart(@RequestBody List<CartLocal> cart, Principal principal){
		try {
			return shopService.getCart(cart, principal.getName());
		} catch (Exception e) {
			throw new ResponseStatusException(
			           HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("user/cart/{s}")
	public List<CartDTO> deleteCart(@PathVariable String s, Principal principal){
		try {
			return shopService.deleteCart(s, principal.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(
			           HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("user/cart/all")
	public void deleteCartAll(Principal principal){
		try {
			shopService.deleteAllCart(principal.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(
			           HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("user/cart/update")
	public List<CartDTO> getCart(@RequestBody CartLocal cart, Principal principal){
		try {
			return shopService.updateCart(cart, principal.getName());
		} catch (Exception e) {
			throw new ResponseStatusException(
			           HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("user/account/info")
	public AccountDTO getInfo(Principal principal){
		try {
			return userService.getAccountInfo(principal.getName());
		} catch (Exception e) {
			throw new ResponseStatusException(
			           HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("user/account/order")
	public OrderPage getOrder(Principal principal, @RequestParam Integer c, @RequestParam String f){
		try {
			return shopService.getOrder(principal.getName(), c, f);
		} catch (Exception e) {
			throw new ResponseStatusException(
			           HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("user/account/password")
	@ResponseBody
	public ResponseEntity<?> changePassword(@RequestBody PasswordDTO form, Principal principal){
		try {
			return userPassService.updatePassword(principal.getName(), form);
		}catch (Exception e) {
			throw new ResponseStatusException(
			           HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("user/account/profile")
	public ResponseEntity<?> changeProfile(@RequestPart("form") AccountFormDTO form, Principal principal){
		try {
			userService.updateProfile(form, principal.getName());
			return ResponseEntity.ok().body(new MessageResponse("Cập nhật thành công")) ;
		}catch (Exception e) {
			throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("user/account/image")
	public ResponseEntity<?> changeProfileImage(@RequestPart("file") MultipartFile file, Principal principal){
		try {
			userService.uploadFile(file);
			return ResponseEntity.ok().body(new MessageResponse("")) ;
		}catch (Exception e) {
			throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST);
		}
	}
}
