package com.deskover.model.entity.dto.ecommerce;

import com.deskover.model.entity.database.OrderItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
	public OrderItemDTO(OrderItem orderItem) {
		this.quantity = orderItem.getQuantity();
		this.price = orderItem.getPrice();
		this.item = new Item(orderItem.getProduct());
	}
	
	private int quantity;
	private Item item;
	private double price;
}
