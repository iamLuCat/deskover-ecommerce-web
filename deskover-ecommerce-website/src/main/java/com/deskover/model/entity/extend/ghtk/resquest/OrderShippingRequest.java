package com.deskover.model.entity.extend.ghtk.resquest;

import com.deskover.model.entity.extend.ghtk.OrderShipping;

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
