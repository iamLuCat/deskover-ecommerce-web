package com.deskover.model.entity.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    OrderStatus findByCode(String code);
}