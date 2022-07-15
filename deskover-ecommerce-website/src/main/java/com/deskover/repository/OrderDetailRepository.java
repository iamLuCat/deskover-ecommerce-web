package com.deskover.repository;

import com.deskover.entity.Order;
import com.deskover.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	
	OrderDetail  findByOrder(Order order);
}