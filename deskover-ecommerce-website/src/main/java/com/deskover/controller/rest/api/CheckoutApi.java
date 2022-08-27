package com.deskover.controller.rest.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.model.entity.database.repository.ProductRepository;
import com.deskover.model.entity.dto.ecommerce.CheckoutDto;
import com.deskover.model.entity.dto.security.payload.MessageResponse;
import com.deskover.other.util.ValidationUtil;
import com.deskover.service.CheckoutService;
import com.deskover.service.SessionService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/test")
public class CheckoutApi {
	@Autowired CheckoutService checkoutService;
	@Autowired SessionService sessionService;
	@Autowired ProductRepository productRepo;
	
    @PostMapping("/vnpaycheckout")
    public ResponseEntity<?> test(@RequestBody CheckoutDto items, BindingResult result)  throws Exception{
		if(result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrorsApp(result);
	        return ResponseEntity.badRequest().body(errors);
		}
    	try { 
        	checkoutService.saveOrder(items.getEntity(),items.getTotal(),items.getItems());
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
