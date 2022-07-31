package com.deskover.other.util;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.deskover.service.OrderService;

@Component
public class OrderNumberUtil {

	public static String get() {
		StringBuilder sb = new StringBuilder();
		sb.append("HD-");
		sb.append(gernerateNumber().toString());
		return sb.toString();
	}
	
	public static Integer gernerateNumber() {
		Random rnd = new Random();
		return 10000000 + rnd.nextInt(90000000);
	}
}
