package com.deskover.api.admin;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.configuration.security.payload.response.MessageErrorResponse;
import com.deskover.configuration.security.payload.response.MessageErrorUtil;
import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.entity.Category;
import com.deskover.service.CategoryService;
import com.deskover.util.ValidationUtil;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin")
public class CategoryApi {

    @Autowired
    CategoryService categoryService;


    @GetMapping("/categories")
    public ResponseEntity<?> doGetIsActived(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("isActive") Optional<Boolean> isActive
    ) {
        Page<Category> categories = categoryService.getByActived(isActive.orElse(Boolean.TRUE), page.orElse(0), size.orElse(1));
        if (categories.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse("Not Found Category Activated"));
        }
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/categories/actived")
    public ResponseEntity<?> doGetAllActive() { 
        List<Category> categories = categoryService.getByActived(Boolean.TRUE);
        if (categories.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse("Not Found Category Activated"));
        }
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/categories/datatables")
    public ResponseEntity<?> doGetForDatatables(@Valid @RequestBody DataTablesInput input) {
        return ResponseEntity.ok(categoryService.getAllForDatatables(input));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> doGetById(@PathVariable("id") Long id) {
        Category category = categoryService.getById(id);
        return ResponseEntity.ok(Objects.requireNonNullElseGet(category, () -> new MessageResponse("Not Found Category")));
    }

    @PostMapping("/categories")
    public ResponseEntity<?> doPostCreate(@Valid @RequestBody Category category ,BindingResult result) {
    	if (result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
			return ResponseEntity.badRequest().body(errors);
		}
        if (categoryService.existsBySlug(category)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Slug đã tồn tại"));
        }
        try {
            categoryService.create(category);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/categories")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody Category category,BindingResult result) {
    	if (result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
			return ResponseEntity.badRequest().body(errors);
		}
        if (categoryService.existsBySlug(category)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Slug đã tồn tại"));
        }
        try {
    		
            categoryService.update(category);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
			MessageErrorResponse error = MessageErrorUtil.message("Cập nhập không thành công", e);
			return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> doChangeActive(@PathVariable("id") Long id) {
        try {
        	categoryService.changeActived(id);
   		 return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    
}
