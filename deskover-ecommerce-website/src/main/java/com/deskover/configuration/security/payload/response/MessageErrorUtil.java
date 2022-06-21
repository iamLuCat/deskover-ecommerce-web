package com.deskover.configuration.security.payload.response;

public class MessageErrorUtil {
	public static MessageErrorResponse message(String message, Exception e) {
		MessageErrorResponse error = new MessageErrorResponse();
		error.setMessage(message+ e.getCause().getCause().getLocalizedMessage());
		return error;
		
	}
}
