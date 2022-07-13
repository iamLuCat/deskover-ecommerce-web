package com.deskover.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.OrderStatus;

public interface OrderStatusReponsitory extends JpaRepository<OrderStatus,Long> {
	
	OrderStatus findByCode(String code);

}
