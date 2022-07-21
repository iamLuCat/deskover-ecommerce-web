package com.deskover.service;

import java.util.List;

import com.deskover.entity.Cart;

public interface CartService {
	
	Cart addToCart(String username, Long productId, Integer quantity);

	List<Cart> doGetAllCartOrder(String username);

}
