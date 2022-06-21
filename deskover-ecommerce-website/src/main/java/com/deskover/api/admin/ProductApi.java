package com.deskover.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.entity.Product;
import com.deskover.repository.ProductRepository;
import com.deskover.service.ProductService;

@RestController
@RequestMapping("v1/api/admin")
public class ProductApi {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/products/actived")
	public ResponseEntity<?> doGetAll(@RequestParam("page") Integer page, @RequestParam("items") Integer items) {
		List<Product> products = productService.findByActived(Boolean.TRUE, page, items);
		if (products.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Products Not Found"));
		}
		return ResponseEntity.ok(products);

	}


}