package com.deskover.controller.rest.api.application;

import com.deskover.model.entity.database.Subcategory;
import com.deskover.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("SubcategoryApiForClient")
@RequestMapping("v1/api/display")
public class SubcategoryApi {
	@Autowired
	private SubcategoryService subcategoryService;
	
	@GetMapping("/subcategory")
	public ResponseEntity<?> doGetAll(@RequestParam("categoryId") Long categoryId){
		List<Subcategory> subcategories =  subcategoryService.getAll(Boolean.TRUE, categoryId);
		return ResponseEntity.ok(subcategories);
	}

}
