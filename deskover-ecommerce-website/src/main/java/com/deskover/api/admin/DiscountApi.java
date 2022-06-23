package com.deskover.api.admin;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.deskover.configuration.security.payload.response.MessageErrorResponse;
import com.deskover.configuration.security.payload.response.MessageErrorUtil;
import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.entity.Discount;
import com.deskover.service.DiscountService;
import com.deskover.util.ValidationUtil;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin")
public class DiscountApi {
	
	@Autowired
	private DiscountService discountService;
	
	@GetMapping("/discount")
	public ResponseEntity<?> doGetAll(){
		List<Discount> discounts = discountService.findAll();
		if(discounts.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Không có dữ liệu"));
		}
		return ResponseEntity.ok(discounts);
	}
	
    @PostMapping("/discount/datatables")
    public ResponseEntity<?> doGetForDatatables(@Valid @RequestBody DataTablesInput input) {
        return ResponseEntity.ok(discountService.getAllForDatatables(input));
    }
	
	@GetMapping("/discount/{id}")
	public ResponseEntity<?> doGetDiscountId(@PathVariable("id") Long id){
		Discount discounts = discountService.findById(id);
		if(discounts == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy discount id: "+id));
		}
		return ResponseEntity.ok(discounts);
	}
	
	@PostMapping("/discount")
	public ResponseEntity<?> doCreate(@Valid @RequestBody Discount discount,BindingResult result){
		if (result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
			return ResponseEntity.badRequest().body(errors);
		}
		try {
			discountService.create(discount);
			return ResponseEntity.created(null).body(new MessageResponse("Thêm mới thành công"));
		}catch (Exception e) {
			MessageErrorResponse error = MessageErrorUtil.message("Thêm mới thất bại", e);
			return ResponseEntity.badRequest().body(error);
		}
	}
	
	@PutMapping("/discount")
	public ResponseEntity<?> doUpdate(@RequestBody Discount discount){
		try {
			discountService.update(discount);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			MessageErrorResponse error = MessageErrorUtil.message("Cập nhập không thành công", e);
			return ResponseEntity.badRequest().body(error);
		}
	}
	
	@DeleteMapping("/discount/{id}")
    public ResponseEntity<?> doDelete(@PathVariable("id") Long id) {
        try {
        	Discount discount = discountService.changeActive(id);
        	if (discount ==null) {
        		ResponseEntity.ok(new MessageResponse("Không tìm thất Discount id: " +id));
			}
        	return ResponseEntity.ok(discount);
        } catch (Exception e) {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
