package com.deskover.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.entity.Discount;
import com.deskover.service.DiscountService;

@RestController
@RequestMapping("v1/api/admin")
public class DiscountApi {
	
	@Autowired
	private DiscountService discountService;
	
	@GetMapping("/discount")
	public ResponseEntity<?> doGetAll(){
		List<Discount> discounts = discountService.findAll();
		if(discounts.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Discount Not Found"));
		}
		return ResponseEntity.ok(discounts);
	}
	
	@GetMapping("/discount/{id}")
	public ResponseEntity<?> doGetDiscountId(@PathVariable("id") Long id){
		Discount discounts = discountService.findById(id);
		if(discounts == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Discount Not Found"));
		}
		return ResponseEntity.ok(discounts);
	}
	
	@PostMapping("/discount")
	public ResponseEntity<?> doCreate(@RequestBody Discount discount){
		try {
			discountService.save(discount);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@PutMapping("/discount")
	public ResponseEntity<?> doUpdate(@RequestBody Discount discount){
		try {
			discountService.save(discount);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	

}
