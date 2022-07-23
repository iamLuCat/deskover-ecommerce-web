package com.deskover.api.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.service.PaymentService;

@RestController
@RequestMapping("v1/api/custumer")
public class PaymentAppApi {
	
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("/payment")
	public ResponseEntity<?> doGetAll(){
		return ResponseEntity.ok(paymentService.doGetAll());
	}
	
	@GetMapping("/payment/{id}")
	public ResponseEntity<?> doGetById(@PathVariable("id") Long id){
		return ResponseEntity.ok(paymentService.findById(id));
	}

}
