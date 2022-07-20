package com.deskover.api.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.entity.Product;
import com.deskover.service.ProductService;

@RestController
@RequestMapping("v1/api/custumer")
public class ProductAppApi {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/display-product-new")
	public ResponseEntity<?> doGetAll(){
		List<Product> category =  productService.getProductByCreateAtDesc(Boolean.TRUE);
		
		return ResponseEntity.ok(category);
	}

}
