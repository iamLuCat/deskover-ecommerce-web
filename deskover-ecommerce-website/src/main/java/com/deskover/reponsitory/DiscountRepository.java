package com.deskover.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
	List<Discount> findAllByActived(Boolean isActive);
}