package com.deskover.controller.rest.api.dashboard;

import com.deskover.model.entity.database.Category;
import com.deskover.model.entity.dto.security.payload.MessageResponse;
import com.deskover.other.util.MessageErrorUtil;
import com.deskover.other.util.ValidationUtil;
import com.deskover.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController("CategoryApiForAdmin")
@CrossOrigin("*")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'SELLER')")
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
    public ResponseEntity<?> doGetForDatatablesByActive(@Valid @RequestBody DataTablesInput input, @RequestParam("isActive") Optional<Boolean> isActive) {
        return ResponseEntity.ok(categoryService.getByActiveForDatatables(input, isActive.orElse(Boolean.TRUE)));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> doGetById(@PathVariable("id") Long id) {
        Category category = categoryService.getById(id);
        return ResponseEntity.ok(Objects.requireNonNullElseGet(category, () -> new MessageResponse("Not Found Category")));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/categories")
    public ResponseEntity<?> doPostCreate(@Valid @RequestBody Category category, BindingResult result) {
        if (result.hasErrors()) {
            MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            categoryService.create(category);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/categories")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody Category category, BindingResult result) {
        if (result.hasErrors()) {
            MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            categoryService.update(category);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
        	MessageResponse error = MessageErrorUtil.message("Cập nhập không thành công", e);
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> doChangeActive(@PathVariable("id") Long id) {
        try {
            categoryService.changeActived(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
