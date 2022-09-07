package com.deskover.controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.deskover.service.UserService;

@Controller
public class AuthController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/login")
	public String login(Model model, HttpSession session) {
		Object error = session.getAttribute("error");
		Object message = session.getAttribute("message");
		if(!Objects.isNull(error)) {
			model.addAttribute("error", error);
			session.removeAttribute("error");
		}
		if(!Objects.isNull(message)) {
			model.addAttribute("message", message);
			session.removeAttribute("message");
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		return "redirect:/index";
	}
	
	@GetMapping("/oauth2/login/success")
	public String success(OAuth2AuthenticationToken oauth2) {
		userService.loginFromOAuth2(oauth2);
		return "forward:/index";
	}

}
