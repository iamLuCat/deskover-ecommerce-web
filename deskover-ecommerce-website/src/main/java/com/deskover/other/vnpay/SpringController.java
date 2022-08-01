package com.deskover.other.vnpay;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.deskover.model.entity.dto.ProductDto;
import com.deskover.service.SessionService;

@Controller
public class SpringController {
		@Autowired SessionService sessionService;
		
		@GetMapping("/payment")
		public String index(Model model) {
			List<ProductDto>  items = sessionService.get("items");
			long total = 0;
			for (ProductDto productDto : items) {
				total += productDto.getPrice()*productDto.getQuantity();
			}
			System.out.println("total:" + total);
			model.addAttribute("total",total);
			return "vnpay";
		} 
}
