package com.deskover.service;

import java.util.List;

import com.deskover.dto.OrderDto;
import com.deskover.dto.app.total7dayago.DataTotaPrice7DaysAgo;
import com.deskover.dto.app.total7dayago.Total7DaysAgo;
import com.deskover.entity.Order;

public interface OrderService {

	List<Order> getAll();

	List<Order> getAllOrderStatus(String status);

	OrderDto findByOrderCode(String orderCode, String status);

	String getToTalPricePerMonth();
	
	String getCountOrderPerMonth();

	DataTotaPrice7DaysAgo doGetTotalPrice7DaysAgo();
	
//	List<TotalByCategory> getToTalByCategoty()

}
