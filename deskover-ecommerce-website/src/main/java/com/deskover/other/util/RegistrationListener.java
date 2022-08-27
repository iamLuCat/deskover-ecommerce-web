package com.deskover.other.util;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.deskover.model.entity.database.Users;
import com.deskover.service.impl.VerifyServiceImpl;

@Component
public class RegistrationListener implements   ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired 
	private VerifyServiceImpl otpService;

	@Autowired 
	private JavaMailSender mailSender;
	
	
	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		Users user = event.getUser();
		String token = UUID.randomUUID().toString();
		otpService.createVerifyToken(token, user);
		String receiveEmail = user.getEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl  = event.getAppUrl() + "/regitrationConfirm?token=" + token;
		String message = "Your Token Exist in 24 hour. Click to confirm";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(receiveEmail);
		email.setSubject(subject);
		email.setText(message + "\r\n" + "" + confirmationUrl);
		mailSender.send(email);
	
	}

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

}
