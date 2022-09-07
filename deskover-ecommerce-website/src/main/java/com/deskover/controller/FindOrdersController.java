package com.deskover.controller;

import com.deskover.dto.SmsPojoDto;
import com.deskover.dto.StoreOTPDto;
import com.deskover.dto.TempOTPDto;
import com.deskover.dto.ecommerce.OrderDetailDTO;
import com.deskover.entity.Order;
import com.deskover.service.SessionService;
import com.deskover.service.SmsService;
import com.deskover.service.impl.OrderServiceImpl;
import com.deskover.service.impl.ShopServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class FindOrdersController {
	@Autowired
	private  OrderServiceImpl orderService;
	
	@Autowired 
	private SmsService service;
	
	@Autowired
	private SimpMessagingTemplate webSocket;
	
	@Autowired 
	private SessionService sessionService;
	
	@Autowired
	private ShopServiceImpl shopService;
	
	private final String TOPIC_DESTINATION = "/lesson/sms";
	
	@GetMapping("find")
	public String find() {
		return "find";
	}
	
	@GetMapping("otp")
	public String otp() {
		return "otp";
	}
	
	@GetMapping("ordered")
	public String ordered(Model model) {
		String phone = sessionService.get("phone");
		List<Order> order =  orderService.getByPhone(phone);
		model.addAttribute("orders",order);
		model.addAttribute("phone",phone);
		return "ordered";
	}
	

	@RequestMapping("/orders")
	public String otpvalid(@ModelAttribute("otp") String otp, Model model) {
		try {
			TempOTPDto sms = new TempOTPDto();
			sms.setOtp(Integer.parseInt(otp));
			if(sms.getOtp() == StoreOTPDto.getOtp()) {
				System.out.println("Correct OTP");

				return "redirect:/ordered";
			}
			else {
				model.addAttribute("msg","OTP không đúng vui lòng nhập lại");
				return "otp";
			}
		} catch (Exception e) {
				model.addAttribute("msg","OTP không đúng vui lòng nhập lại");
				return "otp";
		}

	}
	
	@PostMapping("otp")
	public String otpp(@ModelAttribute("phone") String phone, Model model) {
		sessionService.set("phone", phone);
		SmsPojoDto sms = new SmsPojoDto();
		sms.setPhoneNo("+84"+phone.substring(1));
		try {
			service.send(sms);
		} catch (Exception e) {
			model.addAttribute("msg","Số điện thoại không hợp lệ");
			return "find"; 
		}
		webSocket.convertAndSend(TOPIC_DESTINATION,getTimeStamp() + ": SMS has been sent!:" + sms.getPhoneNo());
		System.out.println("gửi thành công");
		return "otp";
	}
	

	@PostMapping("reOtp")
	public String resendotp( Model model) {
		String phone = sessionService.get("phone");
		SmsPojoDto sms = new SmsPojoDto();
		sms.setPhoneNo("+84"+phone.substring(1));
		try {
			service.send(sms);
		} catch (Exception e) {
			model.addAttribute("msg","Số điện thoại không hợp lệ");
			return "find"; 
		}
		webSocket.convertAndSend(TOPIC_DESTINATION,getTimeStamp() + ": SMS has been sent!:" + sms.getPhoneNo());
		System.out.println("gửi thành công");
		return "otp";
	}
	
	private String getTimeStamp() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
	} 
	

	@GetMapping("order/details")
	public String orderDetail(@RequestParam(name = "id") String id, Model model) {
		try {
			OrderDetailDTO od = shopService.getOrderDetail( id);
			model.addAttribute("od", od);
			return "account_order_detail";
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(
			           HttpStatus.FORBIDDEN);
		}
	}
}
