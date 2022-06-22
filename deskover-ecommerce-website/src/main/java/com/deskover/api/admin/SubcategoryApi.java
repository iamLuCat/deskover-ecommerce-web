package com.deskover.api.admin;

import javax.validation.Valid;

import com.deskover.dto.SubcategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin")
public class SubcategoryApi {
	
	@Autowired
	SubcategoryService subcategoryService;

	//Tìm theo id
	@GetMapping("/subcategories/{id}")
	public ResponseEntity<?> doGetById(@PathVariable("id") Long id){
		Subcategory subcategory = subcategoryService.getById(id);
		return ResponseEntity.ok(Objects.requireNonNullElseGet(subcategory, () -> new MessageResponse("Not Found SubCategory")));
	}
	
	//datatable
	@PostMapping("/subcategories/datatables")
    public ResponseEntity<?> doGetForDatatables(@Valid @RequestBody DataTablesInput input) {
        return ResponseEntity.ok(subcategoryService.getAllForDatatables(input));
    }
	
	@PostMapping("/subcategories")
	public ResponseEntity<?> doPostCreate(@Valid @RequestBody SubcategoryDto subcategoryDto, BindingResult result){
		if (result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
			return ResponseEntity.badRequest().body(errors);
		}
		try {
			subcategoryService.create(subcategoryDto);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/subcategories")
	public ResponseEntity<?> updateSubcategory(@RequestBody SubcategoryDto subcategoryDto){
        try {
			subcategoryService.update(subcategoryDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/subcategories/{id}")
	public ResponseEntity<?> doChangeActive(@PathVariable("id") Long id){
		try {
			return ResponseEntity.ok(subcategoryService.changeActive(id));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}
	
}
