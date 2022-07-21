package com.deskover.api.app;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.entity.Product;
import com.deskover.service.ProductService;

@RestController
@RequestMapping("v1/api/custumer")
public class ProductAppApi {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/display-product-new")
	public ResponseEntity<?> doGetAll(@RequestParam("page") Optional<Integer> page,@RequestParam("size") Optional<Integer> size){
		try {
			Page<Product> category =  productService.getProductByCreateAtDesc(Boolean.TRUE, page, size);
			return ResponseEntity.ok(category);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(),e);
		} 
		
	}

}
