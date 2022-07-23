package com.deskover.api.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.entity.Subcategory;
import com.deskover.service.SubcategoryService;

@RestController
@RequestMapping("v1/api/custumer")
public class SubcategoryAppApi {
	@Autowired
	private SubcategoryService subcategoryService;
	
	@GetMapping("/display-subcategory")
	public ResponseEntity<?> doGetAll(@RequestParam("categoryId") Long categoryId){
		List<Subcategory> subcategories =  subcategoryService.getAll(Boolean.TRUE, categoryId);
		return ResponseEntity.ok(subcategories);
	}

}
