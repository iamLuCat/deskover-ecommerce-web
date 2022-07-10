package com.deskover.service.impl;

import java.time.YearMonth;
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
		Order order = repository.findByOrderCodeAndOrderStatusCode(orderCode, status);
		if(order == null) {
			throw new IllegalArgumentException("Không tìm thấy sản phẩm");
			
		}
		return order;
	}

	@Override
	public String getToTalPricePerMonth(String userModified) {
	
		YearMonth currentTimes = YearMonth.now();
		
		String month = currentTimes.getMonthValue()+"";
		String year = currentTimes.getYear()+"";
		return repository.getToTalPricePerMonth(month, year, userModified);
	
		
	}

}
