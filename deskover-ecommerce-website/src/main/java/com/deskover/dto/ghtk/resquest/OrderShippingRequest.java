package com.deskover.dto.ghtk.resquest;

import com.deskover.dto.ghtk.entity.OrderShipping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderShippingRequest {
	private Boolean success;
	private String message;
	private OrderShipping order;
	private String warning_message;
}
