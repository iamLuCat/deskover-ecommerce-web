package com.deskover.service;

import java.util.List;

import com.deskover.entity.OrderStatus;

public interface OrderStatusService {
	List<OrderStatus> doGetAll();
	OrderStatus findById(Long id);
	OrderStatus doGetByStatusCode(String code);

}
