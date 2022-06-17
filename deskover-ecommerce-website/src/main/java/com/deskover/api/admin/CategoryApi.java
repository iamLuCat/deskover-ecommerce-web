package com.deskover.api.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deskover.entity.Category;
import com.deskover.repository.CategoryRepository;
import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.service.CategoryService;

@RestController
@RequestMapping("v1/api/admin")
public class CategoryApi {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	private CategoryRepository repository;

	/**
	 * Get categories is activated
	 * @return List<Category>
	 */
	@GetMapping("/categories/activated")
	public ResponseEntity<?> doGetIsActivated(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
		List<Category> categories = categoryService.getByActived(Boolean.TRUE, page.orElse(0), size.orElse(5));
		if (categories.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("Not Found Category Activated"));
		}
		return ResponseEntity.ok(categories)  ;
	}

	/**
	 * Get category is unactivated
	 * @return Category
	 */
	@GetMapping("/categories/unactivated")
	public ResponseEntity<?> doGetIsUnactivated(){
		List<Category> categories = categoryService.getByActived(Boolean.FALSE);
		if (categories.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("Not Found Category UnActived"));
		}
		return ResponseEntity.ok(categories)  ;
	}

	/**
	 * Get category by id
	 * @param id category id
	 * @return Category
	 */
	@GetMapping("/categories/{id}")
	public ResponseEntity<?> doGetById(@PathVariable("id") Long id){
		Category category = categoryService.getById(id);
		if (category == null) {
			return ResponseEntity.ok(new MessageResponse("Not Found Category"));
		}
		return ResponseEntity.ok(category);
	}

	/**
	 * Create category
	 * @param category to create
	 * @return Category created
	 */
	@PostMapping("/categories")
	public ResponseEntity<?> doPostCreate(@RequestBody Category category){
		try {
			categoryService.update(category);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Update category
	 * @param category to update
	 * @return Category updated
	 */
	@PutMapping("/categories")
	public ResponseEntity<?> updateCategory(@RequestBody Category category){
		try {
			categoryService.update(category);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
}
