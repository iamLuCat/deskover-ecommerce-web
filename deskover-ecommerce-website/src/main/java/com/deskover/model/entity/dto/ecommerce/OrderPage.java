package com.deskover.model.entity.dto.ecommerce;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.deskover.model.entity.database.Order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPage {
	
	public OrderPage(Page<Order> orders) {
		this.totalPage = orders.getTotalPages();
		this.list = orders.toList().stream().map(order -> new OrderDTO(order)).collect(Collectors.toList());
	}
	
	private int totalPage;
	private List<OrderDTO> list;
}
