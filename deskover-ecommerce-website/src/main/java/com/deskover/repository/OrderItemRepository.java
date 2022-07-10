package com.deskover.repository;

import com.deskover.entity.OrderItem;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	
	List<OrderItem> findByOrderId(Long id);
}