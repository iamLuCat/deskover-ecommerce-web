package com.deskover.controller.ecommerce;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.deskover.model.entity.database.UserAddress;
import com.deskover.model.entity.other.MailInfo;
import com.deskover.other.util.DateUtil;
import com.deskover.other.util.MailUtil;
import com.deskover.service.SessionService;

@Controller
public class SendInvoiceController {
    @Autowired ServletContext app;
	@Autowired MailUtil mailUtil;
	@Autowired SessionService sessionService;
	
	@RequestMapping(value = "/send")
	public String submit(Model model) throws IllegalStateException, IOException, MessagingException {
			UserAddress entity = new UserAddress();
			entity = sessionService.get("address");
			
        	String to = entity.getEmail();
        	model.addAttribute("user",entity);
        	
			MailInfo mailInfo = new MailInfo();
			mailInfo.setFrom(MailUtil.FROM);
			mailInfo.setTo(to);
			mailInfo.setSubject("Thông tin đơn hàng của bạn");
			mailInfo.setBody("Đã xác nhận đơn hàng, vui lòng kiểm tra hóa đơn");
			String filename = "invoice.png"; //đưa tên file vào
			Path path = Paths.get("src\\main\\webapp\\uploads\\"+filename);
			String name = filename; 
			String originalFileName = filename;
  
			byte[] content = null;
			try {
			    content = Files.readAllBytes(path);
			} catch (final IOException e) { }
			
			MultipartFile at = new MockMultipartFile(name,  originalFileName, filename, content);
			MultipartFile[] att = {at};
			mailInfo.setAttachments(att); 
			mailUtil.push(mailInfo);
			
			return "redirect:/ok";
	}
	

	@RequestMapping("/invoice")
	public String invoice(Model model) {
		UserAddress entity = new UserAddress();
		entity = sessionService.get("address");
		String status = sessionService.get("status");
		
		model.addAttribute("status",status);
		model.addAttribute("address",entity);
		model.addAttribute("time", DateUtil.FormatDate());
		
		return "invoice";
	}
}
