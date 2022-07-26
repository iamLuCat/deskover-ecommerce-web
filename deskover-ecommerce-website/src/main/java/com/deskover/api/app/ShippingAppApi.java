package com.deskover.api.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.service.ShippingService;

@RestController
@RequestMapping("v1/api/customer")
public class ShippingAppApi {
	
	@Autowired
	private ShippingService shippingService;
	
	@GetMapping("/shipping")
	public ResponseEntity<?> doGetAll(){
		return ResponseEntity.ok(shippingService.doGetAll());
	}
	
	@GetMapping("/shipping /{id}")
	public ResponseEntity<?> doGetById(@PathVariable("id") Long id){
		return ResponseEntity.ok(shippingService.findById(id));
	}

}
