package com.deskover.controller.ecommerce;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("account")
public class AccountController {

	@GetMapping("profile")
	public String profile() {
		return "account_profile";
	}
	
	@GetMapping("order")
	public String order() {
		return "account_order";
	}
	
	@GetMapping("wishlist")
	public String wishlist() {
		return "account_wishlist";
	}
	
	@GetMapping("password")
	public String changePassword() {
		return "account_password";
	}
	
	
	
}
