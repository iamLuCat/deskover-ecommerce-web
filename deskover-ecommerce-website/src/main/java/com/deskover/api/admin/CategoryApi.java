package com.deskover.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.service.CategoryService;

@RestController
@RequestMapping("v1/api/admin")
public class CategoryApi {
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/categories")
	public ResponseEntity<?> doGetAll(){
		return null;
	}
	
}
