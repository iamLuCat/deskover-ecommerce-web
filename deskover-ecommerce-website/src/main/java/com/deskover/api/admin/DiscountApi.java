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
	
	@GetMapping("/discounts")
	public ResponseEntity<?> doGetAll(){
		List<Discount> discounts = discountService.findAll();
		if(discounts.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Không có dữ liệu"));
		}
		return ResponseEntity.ok(discounts);
	}
	
    @PostMapping("/discounts/datatables")
    public ResponseEntity<?> doGetForDatatablesByActive(@Valid @RequestBody DataTablesInput input, @RequestParam("isActive") Optional<Boolean> isActive) {
        return ResponseEntity.ok(discountService.getByActiveForDatatables(input, isActive.orElse(Boolean.TRUE)));
    }
	
	@GetMapping("/discounts/{id}")
	public ResponseEntity<?> doGetDiscountId(@PathVariable("id") Long id){
		Discount discounts = discountService.findById(id);
		if(discounts == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy discount id: "+id));
		}
		return ResponseEntity.ok(discounts);
	}
	
	@PostMapping("/discounts")
	public ResponseEntity<?> doCreate(@Valid @RequestBody Discount discount,BindingResult result){
		if (result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
			return ResponseEntity.badRequest().body(errors);
		}
		try {
			discountService.create(discount);
			return ResponseEntity.created(null).body(new MessageResponse("Thêm mới thành công"));
		}catch (Exception e) {
			MessageResponse error = MessageErrorUtil.message("Thêm mới thất bại ", e);
			return ResponseEntity.badRequest().body(error);
		}
	}
	
	@PutMapping("/discounts")
	public ResponseEntity<?> doUpdate(
			@RequestBody Discount discount,
			@RequestParam("productIdToAdd") Optional<Long> productIdToAdd,
			@RequestParam("productIdToRemove") Optional<Long> productIdToRemove
	){
		try {
			discountService.update(
					discount,
					productIdToAdd.orElse(null),
					productIdToRemove.orElse(null)
			);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			MessageResponse error = MessageErrorUtil.message("Cập nhập không thành công", e);
			return ResponseEntity.badRequest().body(error);
		}
	}
	
	@DeleteMapping("/discounts/{id}")
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
