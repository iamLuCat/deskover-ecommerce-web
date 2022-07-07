package com.deskover.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.entity.Order;
import com.deskover.repository.OrderRepository;
import com.deskover.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository repository;

	@Override
	public List<Order> getAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public List<Order> getAllOrderStatus(String status) {
		return repository.findByOrderStatusCode(status);
	}

	@Override
	public Order findByOrderCode(String orderCode, String status) {
		// TODO Auto-generated method stub
		Order order = repository.findByOrderCodeContainingAndOrderStatusCodeContaining(orderCode, status);
		if(order != null) {
			return order;
		}
		throw new IllegalArgumentException("Không tìm thấy sản phẩm");
	}

}
