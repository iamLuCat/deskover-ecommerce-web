package com.deskover.controller.rest.api.application;

import com.deskover.model.entity.database.Category;
import com.deskover.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("CategoryApiForClient")
@RequestMapping("v1/api/display")
public class CategoryApi {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/category")
	public ResponseEntity<?> doGetAll(){
		List<Category> category =  categoryService.getByActived(Boolean.TRUE);
		return ResponseEntity.ok(category);
	}

}
