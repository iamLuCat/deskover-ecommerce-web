package com.deskover.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("checkout")
@Controller
public class CheckoutController {
	
	@GetMapping
	public String checkout() {
		return "checkout";
	}
	
}
