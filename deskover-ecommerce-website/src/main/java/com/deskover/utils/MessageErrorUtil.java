package com.deskover.utils;

import com.deskover.dto.MessageResponse;

public class MessageErrorUtil {
	public static MessageResponse message(String message, Exception e) {
		MessageResponse error = new MessageResponse();
		if(e.getCause() != null) {
			error.setMessage( message +": " + e.getCause().getCause().getLocalizedMessage());
			return error;
		}
		error.setMessage(e.getMessage());
		return error;
	}
}
