package com.deskover.api.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.entity.Category;
import com.deskover.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryApiCustomer {
	
	@Autowired
	private CategoryService categoryModel;
	
	@GetMapping("/all")
	public List<Category> getAll(){
		return categoryModel.getByActived(true);
	}
	
}
