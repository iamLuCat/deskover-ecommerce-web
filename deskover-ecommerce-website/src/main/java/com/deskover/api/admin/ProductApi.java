package com.deskover.api.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.deskover.configuration.security.payload.response.MessageErrorResponse;
import com.deskover.configuration.security.payload.response.MessageErrorUtil;
import com.deskover.configuration.security.payload.response.MessageResponse;
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

	@GetMapping("/product/actived")
	public ResponseEntity<?> doGetAll(@RequestParam("page") Integer page, @RequestParam("items") Integer items) {
		List<Product> products = productService.findByActived(Boolean.TRUE, page, items);
		if (products.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy sản phẩm"));
		}
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
    public ResponseEntity<?> doGetForDatatables(@Valid @RequestBody DataTablesInput input) {
        return ResponseEntity.ok(productService.getAllForDatatables(input));
    }
    
//    @PostMapping("/product")
//    public ResponseEntity<?> doPostCreate(){
//    	
//    }
    
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
			MessageErrorResponse error = MessageErrorUtil.message("Cập nhập không thành công", e);
			return ResponseEntity.badRequest().body(error);
		}
    }



}