package com.deskover.model.entity.database.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	
	List<OrderItem> findByOrderId(Long id);
}