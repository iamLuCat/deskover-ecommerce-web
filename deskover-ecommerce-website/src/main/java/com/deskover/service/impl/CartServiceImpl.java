package com.deskover.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.entity.Cart;
import com.deskover.entity.Product;
import com.deskover.entity.User;
import com.deskover.repository.CartRepository;
import com.deskover.repository.UserRepository;
import com.deskover.service.CartService;
import com.deskover.service.ProductService;

@Service
public class CartServiceImpl implements CartService {
	@Autowired 
	private CartRepository cartRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public Cart addToCart(Long userId, Long productId, Integer quantity) {
		try {
			Product product = productService.findById(productId);
			
			if(product==null) {
				throw new IllegalArgumentException("Không tìm thấy user");
			}
			
			Cart cart = cartRepository.findByProductIdAndUserId(productId, userId);
			if(cart == null) {
				User user = userRepository.getById(userId);
				Cart cartNew = new Cart();
				cartNew.setProduct(product);
				cartNew.setUser(user);
				cartNew.setQuantity(quantity);	
				cartRepository.saveAndFlush(cartNew);
				return cart;
			}
			if(cart.getQuantity()>0) {
				cart.setQuantity(cart.getQuantity() + quantity);
				cartRepository.saveAndFlush(cart);
				return cart;
			}
			return null;
		} catch (Exception e) {
			System.out.println(e);
			throw new IllegalArgumentException("Thêm mới thất bại");
		}
	
	}
	
}
