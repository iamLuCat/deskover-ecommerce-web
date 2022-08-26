package com.deskover.controller.ecommerce;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.model.entity.dto.ecommerce.OrderDetailDTO;
import com.deskover.service.ShopService;

@Controller
@RequestMapping("account")
public class AccountController {
	
	@Autowired
	ShopService service;

	@GetMapping("profile")
	public String profile() {
		return "account_profile";
	}
	
	@GetMapping("order")
	public String order() {
		return "account_order";
	}
	
	@GetMapping("order/detail")
	public String orderDetail(@RequestParam(name = "id") String id, Model model, Principal principal) {
		try {
			OrderDetailDTO od = service.getOrderDetail(principal.getName(), id);
			model.addAttribute("od", od);
			return "account_order_detail";
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(
			           HttpStatus.FORBIDDEN);
		}
		
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
