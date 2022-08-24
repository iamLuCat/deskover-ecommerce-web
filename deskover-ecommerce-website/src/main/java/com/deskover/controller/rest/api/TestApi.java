package com.deskover.controller.rest.api;


import com.deskover.model.entity.dto.security.payload.MessageResponse;
import com.deskover.model.entity.other.MailInfo;
import com.deskover.other.util.MailUtil;
import com.deskover.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import javax.mail.MessagingException;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/test")
public class TestApi {
	@Autowired
    private MailUtil mailUtil;
	@Autowired
	private OrderService orderService;

    @GetMapping("/email")
    public ResponseEntity<?> test(@RequestParam String to) throws IllegalStateException, IOException {
        System.out.println(to);
        try {
            MailInfo mailInfo = new MailInfo();
            mailInfo.setFrom(MailUtil.FROM);
            mailInfo.setTo(to);
            mailInfo.setSubject("Test");
            mailInfo.setBody("Hello World");
            
            mailUtil.push(mailInfo);
            return ResponseEntity.ok(new MessageResponse("Send email successfully"));
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
   
	@PostMapping("/order/cancel/{orderCode}")
	public ResponseEntity<?> doPostCancelOrder1(@PathVariable("orderCode") String orderCode, 
			@RequestParam("statusOrder") String statusOrder){
		try {
			if(statusOrder.equals("C-HUY")){
				 orderService.cancelOrderByUserAndOrderCode1(orderCode,statusOrder);
				return ResponseEntity.ok(new MessageResponse("Đơn hàng của bạn trạng thái chờ huỷ"));
			} 
			if (statusOrder.equals("CANCEL-C-HUY")) {
				orderService.cancelOrderByUserAndOrderCode(orderCode,statusOrder);
				return ResponseEntity.ok(new MessageResponse("Cập nhập đơn hàng thành công"));
			}
			return ResponseEntity.ok(new MessageResponse("Đơn hàng sai trạng thái"));		
		} catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
		}
	}
}
