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
import com.deskover.security.payload.MessageResponse;
import com.deskover.service.SubcategoryService;

@RestController
@RequestMapping("v1/api/admin")
public class SubcategoryApi {
	
	@Autowired
	SubcategoryService subcategoryService;
	
	/**
	 * @return Trả về subcategories đã được kích hoạt
	 */
	
	@GetMapping("/subcategories/actived")
	public ResponseEntity<?> doGetAllActived(){
		List<Subcategory> subcategories = subcategoryService.findAllActived(Boolean.TRUE);
		if (subcategories.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("Not Found SubCategory Actived"));
		}
		return ResponseEntity.ok(subcategories)  ;
	}
	
	/**
	 * @return Trả về subcategories chưa được kích hoạt
	 */
	
	@GetMapping("/subcategories/unactived")
	public ResponseEntity<?> doGetAllUnActived(){
		List<Subcategory> subcategories = subcategoryService.findAllActived(Boolean.FALSE);
		if (subcategories.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("Not Found SubCategory UnActived"));
		}
		return ResponseEntity.ok(subcategories)  ;
	}
	
	/**
	 * @param id:truyền vào Id của subcategory
	 * @return category cần tìm.
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
	 * @param subcategory 
	 * @return 
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
	 * @param category
	 * @return
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
