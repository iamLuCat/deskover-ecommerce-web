package com.deskover.api.admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.configuration.security.payload.response.MessageErrorUtil;
import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.dto.ProductDto;
import com.deskover.entity.Product;
import com.deskover.service.ProductService;
import com.deskover.util.ValidationUtil;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin")
public class ProductApi {

	@Autowired
	private ProductService productService;
	
	
	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/product/active")
	public ResponseEntity<?> doGetAll(@RequestParam("page") Integer page, @RequestParam("items") Integer items) {
		List<Product> products = productService.findByActived(Boolean.TRUE, page, items);
		if (products.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy sản phẩm"));
		}
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/product/subcategory")
	public ResponseEntity<?> doGetBySubcategory() {
		List<Product> products = productService.findBySubcategoryId((long) 1);
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<?> doGetById(@PathVariable("id") Long id){
		Product product = productService.findById(id);
		if(product==null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy sản phẩm id:" + id));
		}
		return ResponseEntity.ok(product);
	}
	
    
    @PostMapping("/product/datatables")
    public ResponseEntity<?> doGetForDatatablesByActive(@Valid @RequestBody DataTablesInput input, @RequestParam("isActive") Optional<Boolean> isActive) {
        return ResponseEntity.ok(productService.getByActiveForDatatables(input, isActive.orElse(Boolean.TRUE)));
    }
    
    @PostMapping("/product")
    public ResponseEntity<?> doPostCreate(@RequestBody ProductDto productDto, BindingResult result){
    	if(result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
			return ResponseEntity.badRequest().body(errors);
    	}
    	try {
    		productService.create(productDto);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
    	
    }
    
    @PutMapping("/product")
    public ResponseEntity<?> doPutUpdate(@RequestBody Product product,BindingResult result){
    	if (result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
			return ResponseEntity.badRequest().body(errors);
		}
        if (productService.existsBySlug(product)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Slug đã tồn tại"));
        }
        try {
        	productService.update(product);
             return ResponseEntity.ok(new MessageResponse("Cập nhập sản phẩm thành công"));
		} catch (Exception e) {
			MessageResponse error = MessageErrorUtil.message("Cập nhập không thành công", e);
			return ResponseEntity.badRequest().body(error);
		}
    }
    
    @PutMapping("product/{id}")
    public ResponseEntity<?> changeActiveSubcategoty(@PathVariable("id") Long id){
    	try {
    		productService.changeActiveSubcategoty(id);
    		return ResponseEntity.ok(new MessageResponse("Cập nhập thành công"));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
    	
    }
    
    @DeleteMapping("product/{id}")
    public ResponseEntity<?> doDeleteById(@PathVariable("id") Long id){
    	try {
    		return ResponseEntity.ok(productService.changeActive(id));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
    }
    
    



}