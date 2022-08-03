package com.deskover.controller.ecommerce;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping("")
	public String home() {
		return "redirect:index";
	}

	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/cart")
	public String cart() {
		return "cart";
	}
	
	@GetMapping("/shop")
	public String shop() {
		return "shop";
	}
	
	@GetMapping("/item")
	public String shopDetail() {
		return "item";
	}
	
	@GetMapping("/order")
	public String order() {
		return "order";
	}
	
	@GetMapping("/user")
	public String user() {
		return "user";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/ok")
	public String ok() {
		return "ok";
	}
}
