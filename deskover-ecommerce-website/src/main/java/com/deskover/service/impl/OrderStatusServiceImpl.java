package com.deskover.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.entity.OrderStatus;
import com.deskover.repository.OrderStatusReponsitory;
import com.deskover.service.OrderStatusService;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {
	@Autowired
	private OrderStatusReponsitory orderStatusReponsitory;

	@Override
	public List<OrderStatus> doGetAll() {
		return orderStatusReponsitory.findAll();
	}

	@Override
	public OrderStatus findById(Long id) {
		return orderStatusReponsitory.getById(id);
	}

	@Override
	public OrderStatus doGetByStatusCode(String code) {
		return orderStatusReponsitory.findByCode(code);
	}

}
