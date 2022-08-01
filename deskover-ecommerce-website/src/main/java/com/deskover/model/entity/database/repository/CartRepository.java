package com.deskover.model.entity.database.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart  findByProductIdAndUserUsername(Long productId, String username);
	List<Cart> findByUserUsername(String username);
}