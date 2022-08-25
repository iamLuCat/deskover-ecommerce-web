package com.deskover.controller.rest.api.application;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.model.entity.database.Users;
import com.deskover.model.entity.database.Verify;
import com.deskover.model.entity.database.repository.UserRepository;
import com.deskover.model.entity.database.repository.VerifyRepository;
import com.deskover.model.entity.dto.SmsPojoDto;
import com.deskover.model.entity.dto.UserCreateDto;
import com.deskover.other.util.OnRegistrationCompleteEvent;
import com.deskover.service.SmsService;
import com.deskover.service.VerifyService;
import com.deskover.service.impl.UserServiceImpl;
import com.deskover.service.impl.VerifyServiceImpl;

@RestController
@RequestMapping("v0/api/customer")
public class RegisterApi {
	@Autowired private UserServiceImpl userService;
	@Autowired private UserRepository userRepo;
	@Autowired private ApplicationEventPublisher eventPublisher;
	@Autowired private VerifyServiceImpl otpService;
    @Autowired private MessageSource messages;
	@Autowired private SmsService service;
	
	@PostMapping("/register")
	public ResponseEntity<?> postRegister(@RequestBody() UserCreateDto newUser, HttpServletRequest request,Model model ) throws ParseException  {
		try {
			Users registered =  userService.create1(newUser);
			SmsPojoDto phone = new SmsPojoDto();
			phone.setPhoneNo("+84"+registered.getUsername().substring(1));	
			otpService.createVerifyToken(service.sendOTP(phone).toString(), registered);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IllegalArgumentException  e) {
			System.out.println(e);
			model.addAttribute("message", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
		}
	}
	
	@GetMapping("/registrationConfirm/{otp}")
	public  ResponseEntity<?> confirmRegistration(@PathVariable("otp") String otp, @RequestParam("phone") String phone) {
		try {
			Verify verificationToken = otpService.getVerificationToken(otp); //  Truyền String token vào để Get entity Token
		    if(verificationToken == null) {
		    	throw new IllegalArgumentException("Sai mã OTP");
		    }
			Users user =  verificationToken.getUser();
			user.setVerify(true);
			user.setActived(true);
			userRepo.save(user);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
		}
	}
	
	
//	@GetMapping("/resend/{phone}")
//	public  ResponseEntity<?> confirmRegistration(@PathVariable("phone") String phoneResponse) {
//		try {
//			SmsPojoDto phone = new SmsPojoDto();
//			phone.setPhoneNo("+84"+ phoneResponse.substring(1));	
//			otpService.createVerifyToken(service.sendOTP(phone).toString(), registered);
//			return new ResponseEntity<>(HttpStatus.OK);
//			return ResponseEntity.ok(user);
//		} catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
//		}
//	}

}
