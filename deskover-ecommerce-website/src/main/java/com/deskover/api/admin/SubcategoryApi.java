package com.deskover.api.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.entity.Subcategory;
import com.deskover.service.SubcategoryService;
import com.deskover.util.ValidationUtil;

@RestController
@RequestMapping("v1/api/admin")
public class SubcategoryApi {
	
	@Autowired
	SubcategoryService subcategoryService;

	//Tìm theo id
	@GetMapping("/subcategories/{id}")
	public ResponseEntity<?> doGetById(@PathVariable("id") Long id){
		Subcategory subcategory = subcategoryService.getById(id);
		if (subcategory == null) {
			return ResponseEntity.ok(new MessageResponse("Not Found SubCategory"));
		}else {
			return ResponseEntity.ok(subcategory);
		}		
	}
	
	//datatable
	@PostMapping("/subcategories/datatables")
    public ResponseEntity<?> doGetForDatatables(@Valid @RequestBody DataTablesInput input) {
        return ResponseEntity.ok(subcategoryService.getAllForDatatables(input));
    }
	
	@PostMapping("/subcategories")
	public ResponseEntity<?> doPostCreate(@Valid @RequestBody Subcategory subcategory, BindingResult result){
		if (result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
			return ResponseEntity.badRequest().body(errors);
		}
        if (subcategoryService.existsBySlug(subcategory)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Slug đã tồn tại"));
        }
		try {
			subcategoryService.create(subcategory);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@PutMapping("/subcategories")
	public ResponseEntity<?> updateSubcategory(@RequestBody Subcategory subcategory){
        if (subcategoryService.existsBySlug(subcategory)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Slug đã tồn tại"));
        }
		try {
			subcategoryService.update(subcategory);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@DeleteMapping("/subcategories/{id}")
	public ResponseEntity<?> doDeleteSubcategory(@PathVariable("id") Long id){
		try {
			subcategoryService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
}
