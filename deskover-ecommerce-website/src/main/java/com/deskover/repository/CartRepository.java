package com.deskover.repository;

import com.deskover.entity.Cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart  findByProductIdAndUserUsername(Long productId, String username);
	List<Cart> findByUserUsername(String username);
}