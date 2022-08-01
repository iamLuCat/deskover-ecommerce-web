package com.deskover.other.util;

import com.deskover.model.entity.dto.security.payload.MessageResponse;

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
