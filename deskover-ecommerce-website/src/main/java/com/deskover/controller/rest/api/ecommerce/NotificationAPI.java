package com.deskover.controller.rest.api.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.service.NotificationService;

@RestController
@RequestMapping("/v1/api/customer")
public class NotificationAPI {
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/notify/{userId}")
	public ResponseEntity<?> getNotifications(@PathVariable("userId") Long userId){
		try {
			return ResponseEntity.ok(notificationService.getAllNotifyOfUserId(userId));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/notify/{notifyId}")
	public void getNotify(@PathVariable("notifyId") Long notifyId) {
		notificationService.getNotify(notifyId);
	}
}
