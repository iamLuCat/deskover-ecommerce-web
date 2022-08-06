package com.deskover.controller.rest.api.ecommerce;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.model.entity.database.Notification;
import com.deskover.service.NotificationService;

@RestController
@RequestMapping("/v1/api/customer")
public class NotificationAPI {
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/notify")
	public ResponseEntity<?> getNotifications(){
		try {
			return ResponseEntity.ok(notificationService.getAllNotifyOfUserId());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/notify/ordercode")
	public ResponseEntity<?> getNotificationsByOrderCode(@RequestParam("orderCode") String orderCode){
		try {
			List<Notification> list = notificationService.getAllNotifyByOrderCode(orderCode);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/notify/{notifyId}")
	public void getNotify(@PathVariable("notifyId") Long notifyId) {
		notificationService.getNotify(notifyId);
	}
}
