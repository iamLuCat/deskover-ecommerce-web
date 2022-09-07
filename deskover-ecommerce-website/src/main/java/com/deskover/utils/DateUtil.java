package com.deskover.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	
	public static String FormatDate() {
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return date.format(formatter);
	} 

}
