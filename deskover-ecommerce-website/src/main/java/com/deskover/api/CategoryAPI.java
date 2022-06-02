package com.deskover.api;

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
import com.deskover.service.CategoryService;

@RestController
@RequestMapping("v1/api/admin")
public class CategoryAPI {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CategoryRepository repository;
	
	@GetMapping("/categories")
	public List<Category> doGetAll(){
		return categoryService.findAll();
	}
	
	@GetMapping("/categories/{id}")
	public ResponseEntity<?> doGetById(@PathVariable("id") Long id){
		Category category = repository.findById(id).get();
		return ResponseEntity.ok(category);
	}
	
	//localhost:8080/v1/api/admin/categories
	@PostMapping("/categories")
	public ResponseEntity<?> doPostCreate(@RequestBody Category category){
		try {
			categoryService.save(category);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@PutMapping("/categories")
	public ResponseEntity<?> updateCategory(@RequestBody Category category){
		try {
			categoryService.save(category);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
}
