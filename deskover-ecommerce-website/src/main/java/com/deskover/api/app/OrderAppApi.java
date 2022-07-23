package com.deskover.api.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.configuration.security.payload.response.MessageErrorUtil;
import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.entity.Order;
import com.deskover.service.OrderService;

@RestController
@RequestMapping("v1/api/custumer")
public class OrderAppApi {
	
	@Autowired
	private OrderService orderService;
	
	
	@PostMapping("/order")
	public ResponseEntity<?> doPostAdd(@RequestBody Order oderResponse){
		try {
			orderService.addOrder(oderResponse, "minhbd");
			return new ResponseEntity<>(HttpStatus.OK);	
		} catch (Exception e) {
            MessageResponse error = MessageErrorUtil.message("Cập nhập không thành công", e);
            return ResponseEntity.badRequest().body(error);
		}
	
	}

}
