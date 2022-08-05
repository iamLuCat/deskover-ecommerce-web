package com.deskover.controller.ecommerce;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.deskover.model.entity.dto.ecommerce.BrandDTO;
import com.deskover.model.entity.dto.ecommerce.FlashSaleDTO;
import com.deskover.model.entity.dto.ecommerce.Item;
import com.deskover.service.ShopService;

@Controller
public class IndexController {
	
	@Autowired
	ShopService shopService;
	
	@GetMapping("")
	public String home() {
		return "redirect:index";
	}

	@GetMapping("/index")
	public String index(Model model) {
		List<Item> listItem1 = shopService.get4TopRate();
		List<Item> listItem2 = shopService.get4TopSale();
		List<Item> listItem3 = shopService.get4TopSold();
		
		model.addAttribute("list1", listItem1);
		model.addAttribute("list2", listItem2);
		model.addAttribute("list3", listItem3);
		
		FlashSaleDTO fs = shopService.getFlashSale();
		List<BrandDTO> brands = shopService.getListBrand();
		
		model.addAttribute("fs", fs);
		model.addAttribute("br", brands);
		
		return "index";
	}
	
	@GetMapping("/cart")
	public String cart() {
		return "cart";
	}
	
	@GetMapping("/shop")
	public String shop(HttpServletRequest request) {
		System.out.println(request.getSession().getAttribute("ngStorage-filter"));
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
	
	@GetMapping("/compare")
	public String compare() {
		return "compare";
	}
	
	@GetMapping("/login")
	public String login() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		
		return "redirect:/index";
	}
}
