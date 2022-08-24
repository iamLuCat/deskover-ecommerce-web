package com.deskover.model.entity.dto.ecommerce;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import com.deskover.model.entity.database.Order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailDTO {
	private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	public OrderDetailDTO(Order order) {
		this.id = order.getOrderCode();
		this.paymentStatus = order.getStatusPayment().getStatus();
		this.psCode = order.getStatusPayment().getCode();
		this.orderStatus = order.getOrderStatus().getStatus();
		this.osCode = order.getOrderStatus().getCode();
		this.total = order.getUnitPrice();
		this.orderDate = this.df.format(order.getCreatedAt());
		this.items = order.getProducts().stream().map(s -> new OrderItemDTO(s)).collect(Collectors.toList());
		order.getProducts().stream().forEach(s -> this.countItem += s.getQuantity());
		this.deliveryDate = order.getEstimated_deliver_time();
		this.pickupDate = order.getEstimated_pick_time();
		this.orderMethod = order.getShipping().getName_shipping();
		
	}
	
	private String id;
	private String orderDate;
	private String deliveryDate;
	private String pickupDate;
	private String paymentStatus;
	private String psCode;
	private String orderStatus;
	private String osCode;
	private Double total;
	private List<OrderItemDTO> items;
	private Integer countItem = 0;
	private String orderMethod;
}
