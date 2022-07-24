package com.deskover.api.admin;

import com.deskover.configuration.security.payload.response.MessageErrorUtil;
import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.entity.Product;
import com.deskover.service.ProductService;
import com.deskover.service.UploadFileService;
import com.deskover.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController("ProductApiForAdmin")
@CrossOrigin("*")
@RequestMapping("v1/api/admin")
public class ProductApi {

    @Autowired
    private ProductService productService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping("/products")
    public ResponseEntity<?> doGetAll(@RequestParam("search") String search,
            @RequestParam("number") Optional<Integer> number,
            @RequestParam("size") Optional<Integer> size) {
        try {
            if (search.isBlank()) {
                Page<Product> products = productService.getByActive(Boolean.TRUE, number, size);
                if (products.isEmpty()) {
                    return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy sản phẩm"));
                }
                return ResponseEntity.ok(products);
            } else {
                Page<Product> products = productService.getByName(search, number, size);
                if (products.isEmpty()) {
                    return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy sản phẩm"));
                }
                return ResponseEntity.ok(products);
            }
        } catch (Exception e) {

            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/products/subcategory")
    public ResponseEntity<?> doGetBySubcategory() {
        List<Product> products = productService.getBySubcategoryId((long) 1);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> doGetById(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy sản phẩm id:" + id));
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping("/products/datatables")
    public ResponseEntity<?> doGetForDatatables(
            @Valid @RequestBody DataTablesInput input,
            @RequestParam("isActive") Optional<Boolean> isActive,
            @RequestParam("categoryId") Optional<Long> categoryId,
            @RequestParam("subcategoryId") Optional<Long> subcategoryId,
            @RequestParam("brandId") Optional<Long> brandId,
            @RequestParam("isDiscount") Optional<Boolean> isDiscount) {
        DataTablesOutput<Product> output = productService.getByActiveForDatatables(
                input,
                isActive.orElse(Boolean.TRUE),
                categoryId.orElse(null),
                brandId.orElse(null),
                isDiscount.orElse(null)
        );
        return ResponseEntity.ok(output);
    }

    @PostMapping("/products")
    public ResponseEntity<?> doPostCreate(@RequestBody Product product, BindingResult result) {
        if (result.hasErrors()) {
            MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            productService.create(product);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/products")
    public ResponseEntity<?> doPutUpdate(@RequestBody Product product, BindingResult result) {
        if (result.hasErrors()) {
            MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        if (productService.existsByOtherSlug(product)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Slug đã tồn tại"));
        }
        try {
            productService.save(product);
            return ResponseEntity.ok(new MessageResponse("Cập nhập sản phẩm thành công"));
        } catch (Exception e) {
            MessageResponse error = MessageErrorUtil.message("Cập nhập không thành công", e);
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> changeActiveSubcategoty(@PathVariable("id") Long id) {
        try {
            productService.changeActiveSubcategoty(id);
            return ResponseEntity.ok(new MessageResponse("Cập nhập thành công"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> doDeleteById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(productService.changeActive(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}