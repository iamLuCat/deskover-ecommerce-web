package com.deskover.dto.ecommerce;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.deskover.entity.Order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
	private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	public OrderDTO(Order order) {
		this.id = order.getOrderCode();
		this.paymentStatus = order.getStatusPayment().getStatus();
		this.psCode = order.getStatusPayment().getCode();
		this.orderStatus = order.getOrderStatus().getStatus();
		this.osCode = order.getOrderStatus().getCode();
		this.total = order.getUnitPrice();
		this.orderDate = this.df.format(order.getCreatedAt());
	}
	
	private String id;
	private String orderDate;
	private String paymentStatus;
	private String psCode;
	private String orderStatus;
	private String osCode;
	private Double total;
}
