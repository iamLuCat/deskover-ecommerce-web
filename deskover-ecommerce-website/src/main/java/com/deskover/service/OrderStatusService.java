package com.deskover.service;

import java.util.List;

import com.deskover.model.entity.database.OrderStatus;

public interface OrderStatusService {
	List<OrderStatus> doGetAll();
	OrderStatus findById(Long id);
	OrderStatus doGetByStatusCode(String code);

}
