package com.deskover.api.admin;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.entity.Category;
import com.deskover.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

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
    public ResponseEntity<?> doPostCreate(@RequestBody Category category) {
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
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        if (categoryService.existsBySlug(category)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Slug đã tồn tại"));
        }
        try {
            categoryService.update(category);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> doDelete(@PathVariable("id") Long id) {
        try {
            categoryService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
