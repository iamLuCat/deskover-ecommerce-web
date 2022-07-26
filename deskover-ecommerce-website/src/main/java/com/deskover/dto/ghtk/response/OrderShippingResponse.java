package com.deskover.dto.ghtk.response;

import com.deskover.dto.ghtk.entity.OrderShipping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderShippingResponse {
	Boolean success;
	String message;
	OrderShipping orders;
	String warning_message;
}
