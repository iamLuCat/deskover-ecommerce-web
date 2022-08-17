package com.deskover.controller.ecommerce;

import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.deskover.model.entity.database.Users;
import com.deskover.model.entity.database.Verify;
import com.deskover.model.entity.database.repository.UserRepository;
import com.deskover.model.entity.dto.UserCreateDto;
import com.deskover.other.util.OnRegistrationCompleteEvent;
import com.deskover.service.impl.VerifyServiceImpl;
import com.deskover.service.impl.UserServiceImpl;

@Controller
public class RegisterController {
	@Autowired UserServiceImpl userService;
	@Autowired UserRepository userRepo;
	@Autowired ApplicationEventPublisher eventPublisher;
	@Autowired VerifyServiceImpl otpService;
    @Autowired  MessageSource messages;
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/register")
	public String postRegister(@ModelAttribute("register") UserCreateDto newUser, BindingResult result, HttpServletRequest request,Model model )  {
		try {
			Users registered =  userService.create1(newUser);
			String appUrl = request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, 
			        request.getLocale(), appUrl));
			model.addAttribute("message","Đăng kí thành công, Vui lòng kích hoạt tài khoản bằng email");
			System.out.println("active" + registered.getVerify());
		} catch (IllegalArgumentException  e) {
			System.out.println(e);
			model.addAttribute("message", e.getMessage());
			return "/register";
		}
		return "/login";
	}
	
	@GetMapping("/regitrationConfirm")
	public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
		Locale locale = request.getLocale();
		Verify verificationToken = otpService.getVerificationToken(token); //  Truyền String token vào để Get entity Token
	    Users user =  verificationToken.getUser();
	    Calendar cal =   Calendar.getInstance();
	    // Nếu Token hết hạn: time = 0 
	    if((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	        String messageValue = "Token hết hạn";
	        model.addAttribute("message", messageValue);
	    	return "redirect:/login";
	    }
		user.setVerify(true);
		model.addAttribute("message","Xác thực tài khoản thành công");
		System.out.println("active" + user.getVerify());
		userRepo.save(user);
		
		return "/login";
	}
}
