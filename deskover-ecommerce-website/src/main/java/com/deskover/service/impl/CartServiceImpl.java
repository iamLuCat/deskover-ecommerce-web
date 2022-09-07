package com.deskover.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.entity.Cart;
import com.deskover.entity.Product;
import com.deskover.entity.Users;
import com.deskover.reponsitory.CartRepository;
import com.deskover.reponsitory.UserRepository;
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
	public Cart addToCart(Long productId, Integer quantity) {
		Product product = productService.findById(productId);
		
		if(product==null) {
			throw new IllegalArgumentException("Không tìm thấy sản phẩm ");
		}
	 System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
		Cart cart = cartRepository.findByProductIdAndUserUsername(productId, SecurityContextHolder.getContext().getAuthentication().getName());
		if(cart == null) {
			Users user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			Cart cartNew = new Cart();
			cartNew.setProduct(product);
			cartNew.setUser(user);
			cartNew.setQuantity(quantity);	
			cartRepository.saveAndFlush(cartNew);
			return cart;
		}
		if (cart.getQuantity() ==10) {
			throw new IllegalArgumentException("Giỏ hàng đầy!!");
		}
		if(cart.getQuantity()>0) {
			cart.setQuantity(cart.getQuantity() + quantity);
			cartRepository.saveAndFlush(cart);
			return cart;
		}
		return null;
	
	}

	@Override
	public List<Cart> doGetAllCartOrder() {
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		return cartRepository.findByUserUsername(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@Override
	@Transactional
	public Cart minusCart(Long productId) {
		try {
			Cart cart = cartRepository.findByProductIdAndUserUsername(productId, SecurityContextHolder.getContext().getAuthentication().getName());
			if(cart.getQuantity()>1) {
				cart.setQuantity(cart.getQuantity()-1);
				cartRepository.saveAndFlush(cart);
				return cart;
			}
			return cart;
		} catch (Exception e) {
			throw new IllegalArgumentException("Phương thức không hợp lệ");
		}
	}

	@Override
	@Transactional
	public void deleteCart(Long productId) {
		try {
			Cart cart = cartRepository.findByProductIdAndUserUsername(productId, SecurityContextHolder.getContext().getAuthentication().getName());
			cartRepository.delete(cart);
		} catch (Exception e) {
			throw new IllegalArgumentException("Phương thức không hợp lệ");
		}
	}
	
}
