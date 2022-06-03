package com.deskover.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.entity.Category;
import com.deskover.service.CategoryServiceHai;

@RestController
@RequestMapping("api/categories/")
public class CategoryAPI {
	
	@Autowired
	CategoryServiceHai categoryService;
	
	@GetMapping("all")
	public List<Category> doGetAll(){
		return categoryService.findAll();
	}
}
