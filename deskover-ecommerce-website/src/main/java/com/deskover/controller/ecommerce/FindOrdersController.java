package com.deskover.controller.ecommerce;

import com.deskover.model.entity.database.Order;
import com.deskover.model.entity.dto.SmsPojoDto;
import com.deskover.model.entity.dto.StoreOTPDto;
import com.deskover.model.entity.dto.TempOTPDto;
import com.deskover.service.SessionService;
import com.deskover.service.SmsService;
import com.deskover.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
	
	private final String TOPIC_DESTINATION = "/lesson/sms";
	
	@GetMapping("find")
	public String find() {
		return "find";
	}
	
	@GetMapping("otp")
	public String otp() {
		return "otp";
	}
	

	@PostMapping("/orders")
	public String otpvalid(@ModelAttribute("otp") String otp, Model model) {
		try {
			TempOTPDto sms = new TempOTPDto();
			sms.setOtp(Integer.parseInt(otp));
			if(sms.getOtp() == StoreOTPDto.getOtp()) {
				System.out.println("Correct OTP");
				String phone = sessionService.get("phone");
				List<Order> order =  orderService.getByPhone(phone);
				model.addAttribute("orders",order);
				return "ordered";
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
	
	private String getTimeStamp() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
	} 
}
