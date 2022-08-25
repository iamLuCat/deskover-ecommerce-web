package com.deskover.service;

import com.deskover.model.entity.dto.SmsPojoDto;
import com.deskover.model.entity.dto.StoreOTPDto;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.text.ParseException;

@Component
public class SmsService {
	private final String ACCOUNT_SID = "AC3aec3a58774c3bdd72859cae8b07af3d";
	private final String AUTH_TOKEN = "bf505c2f62728c7179aa3f2cd649c322";
	private final String FROM_NUMBER = "+19147581911";
	
	public void send(SmsPojoDto sms) throws ParseException{
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		
		int number = (int)(Math.random()*(999999-100000+1)+100000);
		String msg = "Your OTP - " + number + " please verify this OTP in your phone";
		
		Message message = Message.creator(new PhoneNumber(sms.getPhoneNo()) , new PhoneNumber(FROM_NUMBER), msg).create();
		
		StoreOTPDto.setOtp(number);
	}
	
	public Integer sendOTP(SmsPojoDto sms) throws ParseException{
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		
		int number = (int)(Math.random()*(999999-100000+1)+100000);
		String msg = "Your OTP - " + number + " please verify this OTP in your phone";
		
		Message message = Message.creator(new PhoneNumber(sms.getPhoneNo()) , new PhoneNumber(FROM_NUMBER), msg).create();
		
		StoreOTPDto.setOtp(number);
		return number;
	}
	
	
	public void receive(MultiValueMap<String, String> smscallback) {
		
	}
}
