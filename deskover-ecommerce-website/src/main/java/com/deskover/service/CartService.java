package com.deskover.service;

import com.deskover.entity.Cart;

public interface CartService {
	
	Cart addToCart(Long userId, Long productId, Integer quantity);

}
