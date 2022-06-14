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

import com.deskover.entity.Subcategory;
import com.deskover.configuration.security.payload.MessageResponse;
import com.deskover.service.SubcategoryService;

@RestController
@RequestMapping("v1/api/admin")
public class SubcategoryApi {
	
	@Autowired
	SubcategoryService subcategoryService;

	/**
	 * Get subcategories is activated
	 * @return List<Subcategory>
	 */
	
	@GetMapping("/subcategories/activated")
	public ResponseEntity<?> doGetIsActivated(){
		List<Subcategory> subcategories = subcategoryService.findByActivated(Boolean.TRUE);
		if (subcategories.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("Not Found SubCategory Actived"));
		}
		return ResponseEntity.ok(subcategories)  ;
	}

	/**
	 * Get subcategory is unactivated
	 * @return Subcategory
	 */
	
	@GetMapping("/subcategories/unactivated")
	public ResponseEntity<?> doGetIsUnactivated(){
		List<Subcategory> subcategories = subcategoryService.findByActivated(Boolean.FALSE);
		if (subcategories.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("Not found Subcategory not activated"));
		}
		return ResponseEntity.ok(subcategories);
	}

	/**
	 * Get subcategory by id
	 * @param id subcategory id
	 * @return Subcategory
	 */
	
	@GetMapping("/subcategories/{id}")
	public ResponseEntity<?> doGetById(@PathVariable("id") Long id){
		Subcategory subcategory = subcategoryService.findById(id);
		if (subcategory == null) {
			return ResponseEntity.ok(new MessageResponse("Not Found SubCategory"));
		}
		return ResponseEntity.ok(subcategory);
	}

	/**
	 * Create subcategory
	 * @param subcategory can be created
	 * @return	Subcategory created
	 */
	
	@PostMapping("/subcategories")
	public ResponseEntity<?> doPostCreate(@RequestBody Subcategory subcategory){
		try {
			subcategoryService.update(subcategory);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Update subcategory
	 * @param subcategory can be updated
	 * @return Subcategory updated
	 */

	@PutMapping("/subcategories")
	public ResponseEntity<?> updateCategory(@RequestBody Subcategory subcategory){
		try {
			subcategoryService.update(subcategory);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
}
