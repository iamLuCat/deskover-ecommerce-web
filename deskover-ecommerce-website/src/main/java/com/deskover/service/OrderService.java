package com.deskover.service;

import java.util.List;

import com.deskover.dto.OrderDto;
import com.deskover.entity.Order;

public interface OrderService {

	List<Order> getAll();

	List<Order> getAllOrderStatus(String status);

	OrderDto findByOrderCode(String orderCode, String status);

	String getToTalPricePerMonth();
	
	String getCountOrderPerMonth();
	
//	List<TotalByCategory> getToTalByCategoty()

}
