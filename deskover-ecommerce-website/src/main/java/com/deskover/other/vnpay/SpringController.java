package com.deskover.other.vnpay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpringController {
		@GetMapping("/payment")
		public String index() {
				return "vnpay";
		} 
}
