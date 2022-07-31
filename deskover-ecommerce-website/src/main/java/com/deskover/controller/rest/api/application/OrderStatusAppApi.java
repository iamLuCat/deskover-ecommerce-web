package com.deskover.controller.rest.api.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.service.OrderStatusService;

@RestController
@RequestMapping("v1/api/display")
public class OrderStatusAppApi {
	@Autowired
	private OrderStatusService orderStatusService;
	
	@GetMapping("/status")
	public ResponseEntity<?> doGetAll(){
		return ResponseEntity.ok(orderStatusService.doGetAll());
	}
	
	@GetMapping("/status/code")
	public ResponseEntity<?> doGetByStatusCode(@RequestParam("code") String code){
		return ResponseEntity.ok(orderStatusService.doGetByStatusCode(code));
	}
	
	@GetMapping("/status/{id}")
	public ResponseEntity<?> doGetById(@PathVariable("id") Long id){
		return ResponseEntity.ok(orderStatusService.findById(id));
	}

}
