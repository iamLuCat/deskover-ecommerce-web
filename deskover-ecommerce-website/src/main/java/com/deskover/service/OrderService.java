package com.deskover.service;

import java.util.List;

import com.deskover.entity.Order;

public interface OrderService {

	List<Order> getAll();

	List<Order> getAllOrderStatus(String status);

	Order findByOrderCode(String orderCode, String status);

	String getToTalPricePerMonth(String userModified);

}
