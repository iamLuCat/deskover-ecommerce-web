package com.deskover.service;

import java.util.List;

import com.deskover.dto.app.order.OrderDto;
import com.deskover.dto.app.order.resquest.DataOrderResquest;
import com.deskover.dto.app.total7dayago.DataTotaPrice7DaysAgo;
import com.deskover.entity.Order;

public interface OrderService {

	List<Order> getAll();

	List<Order> getAllOrderStatus(String status);

	OrderDto findByOrderCode(String orderCode, String status);
	
	DataOrderResquest getListOrder(String status);
	
	DataOrderResquest getListOrderByUser();

	String getToTalPricePerMonth();
	
	String getCountOrderPerMonth();

	DataTotaPrice7DaysAgo doGetTotalPrice7DaysAgo();

	void pickupOrder(String orderCode,String code);
	
//	List<TotalByCategory> getToTalByCategoty()

}
