package com.deskover.service;

import java.text.ParseException;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.deskover.model.entity.dto.SmsPojoDto;
import com.deskover.model.entity.dto.StoreOTPDto;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class SmsService {
	private final String ACCOUNT_SID = "ACca3fff5d859ab9b17e10bacfa996f54c";
	private final String AUTH_TOKEN = "4b0259c193c72235a27474cdc552ba53";
	private final String FROM_NUMBER = "+17815499381";
	
	public void send(SmsPojoDto sms) throws ParseException{
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		
		int number = (int)(Math.random()*(999999-100000+1)+100000);
		String msg = "Your OTP - " + number + " please verify this OTP in your phone";
		
		Message message = Message.creator(new PhoneNumber(sms.getPhoneNo()) , new PhoneNumber(FROM_NUMBER), msg).create();
		
		StoreOTPDto.setOtp(number);
	}
	
	public void receive(MultiValueMap<String, String> smscallback) {
		
	}
}
