package com.deskover.controller.rest.api.application;

import com.deskover.model.entity.database.Product;
import com.deskover.model.entity.dto.security.payload.MessageResponse;
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
	
    @GetMapping("/product")
    public ResponseEntity<?> doGetAll(@RequestParam("search") String search,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        try {
            if (search.isBlank()) {
                return ResponseEntity.ok(new MessageResponse("Nhập tên sản phẩm hoặc thương hiệu bạn cần tìm"));
            } else {
                Page<Product> products = productService.getByName(search, page, size);
                if (products.isEmpty()) {
                    return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy sản phẩm"));
                }
                return ResponseEntity.ok(products);
            }
        } catch (Exception e) {

            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

	@GetMapping("/product-new")
	public ResponseEntity<?> doGetAll(@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		try {
			Page<Product> category = productService.getProductByCreateAtDesc(Boolean.TRUE, page, size);
			return ResponseEntity.ok(category);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@GetMapping("/product-sale")
	public ResponseEntity<?> doGetProductSale(@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		try {
			Page<Product> category = productService.doGetProductSale(page, size);
			return ResponseEntity.ok(category);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@GetMapping("/product-category")
	public ResponseEntity<?> doGetProductByCategoryId(@RequestParam("categoryId") Long categoryId,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
		try {
			Page<Product> category = productService.getProductByCategoryId(Boolean.TRUE, categoryId, page, size);
			return ResponseEntity.ok(category);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@GetMapping("/product-subcategory")
	public ResponseEntity<?> doGetProductBySubId(@RequestParam("subId") Long subId,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
		try {
			Page<Product> category = productService.getProductBySubId(Boolean.TRUE, subId, page, size);
			return ResponseEntity.ok(category);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}

	}

	@GetMapping("/product/{id}")
	public ResponseEntity<?> doGetById(@PathVariable("id") Long id) {
		Product product = productService.findById(id);
		if (product == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy sản phẩm id:" + id));
		}
		return ResponseEntity.ok(product);
	}

}
