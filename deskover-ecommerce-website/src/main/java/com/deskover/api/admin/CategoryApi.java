package com.deskover.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.entity.Category;
import com.deskover.repository.CategoryRepository;
import com.deskover.security.payload.MessageResponse;
import com.deskover.service.CategoryService;

@RestController
@RequestMapping("v1/api/admin")
public class CategoryApi {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	private CategoryRepository repository;
	
	/**
	 * @return Trả về categories đã được kích hoạt
	 */
	
	@GetMapping("/categories/actived")
	public ResponseEntity<?> doGetAllActived(){
		List<Category> categories = categoryService.findAll(Boolean.TRUE);
		if (categories.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("Not Found Category Actived"));
		}
		return ResponseEntity.ok(categories)  ;
	}
	
	/**
	 * @return Trả về categories chưa được kích hoạt
	 */
	
	@GetMapping("/categories/unactived")
	public ResponseEntity<?> doGetAllUnActived(){
		List<Category> categories = categoryService.findAll(Boolean.FALSE);
		if (categories.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("Not Found Category UnActived"));
		}
		return ResponseEntity.ok(categories)  ;
	}
	
	/**
	 * @param id:truyền vào Id của category
	 * @return category cần tìm.
	 */
	
	@GetMapping("/categories/{id}")
	public ResponseEntity<?> doGetById(@PathVariable("id") Long id){
		Category category = categoryService.findById(id);
		if (category == null) {
			return ResponseEntity.ok(new MessageResponse("Not Found Category"));
		}
		return ResponseEntity.ok(category);
	}
	
	/**
	 * @param category 
	 * @return 
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
	 * @param category
	 * @return
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
