package com.deskover.controller.ecommerce;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.deskover.model.entity.database.Users;
import com.deskover.model.entity.database.repository.UserRepository;
import com.deskover.model.entity.dto.UserCreateDto;
import com.deskover.service.impl.UserPasswordServiceImpl;
import com.deskover.service.impl.UserServiceImpl;

@Controller
public class RegisterController {
	@Autowired UserServiceImpl userService;
	@Autowired UserRepository userRepo;
	
	@GetMapping("/register")
	public String register(Model model) {
		return "register";
	}
	

	@PostMapping("/register")
	public String postRegister(Model model,@ModelAttribute("register") UserCreateDto newUser, BindingResult result )  {
		userService.create(newUser);
		return "/shop";
	}
	
}
