package com.deskover.model.entity.dto.ecommerce;

import com.deskover.model.entity.database.Order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
	public OrderDTO(Order order) {
		this.id = order.getOrderCode();
		this.paymentStatus = order.getStatusPayment().getStatus();
		this.orderStatus = order.getOrderStatus().getStatus();
		this.total = order.getUnitPrice();
	}
	
	private String id;
	private String orderDate;
	private String paymentStatus;
	private String orderStatus;
	private Double total;
}
