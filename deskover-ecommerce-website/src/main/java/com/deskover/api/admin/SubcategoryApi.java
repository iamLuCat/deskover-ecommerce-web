package com.deskover.api.admin;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.dto.SubcategoryDto;
import com.deskover.entity.Subcategory;
import com.deskover.service.SubcategoryService;
import com.deskover.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin")
public class SubcategoryApi {
	
	@Autowired
	SubcategoryService subcategoryService;

	@GetMapping("/subcategories/{id}")
	public ResponseEntity<?> doGetById(@PathVariable("id") Long id){
		Subcategory subcategory = subcategoryService.getById(id);
		return ResponseEntity.ok(Objects.requireNonNullElseGet(subcategory, () -> new MessageResponse("Not Found SubCategory")));
	}
	
	@PostMapping("/subcategories/datatables")
    public ResponseEntity<?> doGetForDatatables(@Valid @RequestBody DataTablesInput input) {
        return ResponseEntity.ok(subcategoryService.getAllForDatatables(input));
    }

	@PostMapping("/subcategories/datatables-by-active")
	public ResponseEntity<?> doGetByActiveForDatatables(@Valid @RequestBody DataTablesInput input, @RequestParam("isActive") Optional<Boolean> isActive) {
		return ResponseEntity.ok(subcategoryService.getByActiveForDatatables(input, isActive.orElse(Boolean.TRUE)));
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
