package com.deskover.dto.app.order.resquest;

import java.util.List;

import com.deskover.entity.Order;
import com.deskover.entity.OrderDetail;
import com.deskover.entity.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddOrderResponse {
	 
	 private Order order;
	 
	 private List<OrderItem> item;
	 
	 private OrderDetail address;

}
