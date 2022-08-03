package com.deskover.model.entity.database.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
	List<Discount> findAllByActived(Boolean isActive);
}