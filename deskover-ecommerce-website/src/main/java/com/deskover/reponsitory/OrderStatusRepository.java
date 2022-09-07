package com.deskover.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    OrderStatus findByCode(String code);
}