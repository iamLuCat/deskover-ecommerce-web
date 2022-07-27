package com.deskover.api.app;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.entity.Product;
import com.deskover.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController("ProductApiForClient")
@RequestMapping("v1/api/display")
public class ProductApi {

	@Autowired
	private ProductService productService;

	@GetMapping("product-new")
	public ResponseEntity<?> doGetAll(@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		try {
			Page<Product> category = productService.getProductByCreateAtDesc(Boolean.TRUE, page, size);
			return ResponseEntity.ok(category);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@GetMapping("product-category")
	public ResponseEntity<?> doGetProductByCategoryId(@RequestParam("categoryId") Long categoryId,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
		try {
			Page<Product> category = productService.getProductByCategoryId(Boolean.TRUE, categoryId, page, size);
			return ResponseEntity.ok(category);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@GetMapping("product-subcategory")
	public ResponseEntity<?> doGetProductBySubId(@RequestParam("subId") Long subId,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
		try {
			Page<Product> category = productService.getProductBySubId(Boolean.TRUE, subId, page, size);
			return ResponseEntity.ok(category);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}

	}

	@GetMapping("/display-product/{id}")
	public ResponseEntity<?> doGetById(@PathVariable("id") Long id) {
		Product product = productService.findById(id);
		if (product == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy sản phẩm id:" + id));
		}
		return ResponseEntity.ok(product);
	}

}
