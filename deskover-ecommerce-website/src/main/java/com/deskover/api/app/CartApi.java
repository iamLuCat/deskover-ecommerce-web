package com.deskover.api.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.entity.Cart;
import com.deskover.service.CartService;

@RestController
@RequestMapping("v1/api/customer")
public class CartApi {
	
	@Autowired
	private CartService cartService;
	
	@GetMapping("/cart")
	public ResponseEntity<?> doGetAllCartOrder(@RequestParam("username") String username){
		List<Cart> cart = cartService.doGetAllCartOrder(username);
		return ResponseEntity.ok(cart);
	}
	
	@PostMapping("/add-cart")
	public ResponseEntity<?> doAddToCart(@RequestParam("username") String username, 
			@RequestParam("productId") Long productId, @RequestParam("quantity") Integer quantity){
		try {
			cartService.addToCart(username, productId, quantity);
			return ResponseEntity.ok(new MessageResponse("Thêm mới thành công"));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
		}
	}
	
	@PostMapping("/minus-cart")
	public ResponseEntity<?> doMinusCart(@RequestParam("username") String username, 
			@RequestParam("productId") Long productId){
		try {
			cartService.minusCart(username, productId);
			return ResponseEntity.ok(new MessageResponse("Thêm mới thành công"));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
		}
	}
	
	@DeleteMapping("cart")
	public ResponseEntity<?> doDeleteCart(@RequestParam("username") String username, 
			@RequestParam("productId") Long productId){
		try {
			cartService.deleteCart(username, productId);
			return ResponseEntity.ok(new MessageResponse("Xoá thành công"));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
		}
	}
}
