package com.deskover.model.entity.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}