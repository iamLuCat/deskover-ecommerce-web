package com.deskover.service;

import java.util.List;

import com.deskover.entity.Cart;

public interface CartService {
	
	Cart addToCart(Long productId, Integer quantity);

	List<Cart> doGetAllCartOrder();

	Cart minusCart( Long productId);

	void deleteCart(Long productId);

}
