package com.deskover.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.dto.ecommerce.Item;
import com.deskover.dto.ecommerce.ProductDTO;
import com.deskover.entity.Product;
import com.deskover.service.ProductService;
import com.deskover.service.ShopService;

@RequestMapping("shop")
@Controller
public class ShopController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ShopService shopService;
	
	@GetMapping("item")
	public String item(@RequestParam String p, Model model) {
		if(p.isBlank()) return "redirect:index";
		
		try {
			Product product = productService.findBySlug(p);
			model.addAttribute("product", new ProductDTO(product));
			List<Item> recommendItems = shopService.getRecommendList(product.getSubCategory().getCategory().getId());
			model.addAttribute("recommendItems", recommendItems);
		} catch (Exception e) {
			 throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return "item";
	}
	
}
