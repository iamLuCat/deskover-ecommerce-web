package com.deskover.api.admin;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.entity.Order;
import com.deskover.repository.OrderRepository;
import com.deskover.service.OrderService;
import com.deskover.util.DecimalFormatUtil;

@RestController
@RequestMapping("v1/api/admin")
public class OrderApi {
	
	
		
	@Autowired
	private OrderService orderService;
	
	@Autowired OrderRepository orderRepository;
	
	@RequestMapping("/order")
	public ResponseEntity<?> doGetAll(){
		List<Order> orders = orderService.getAll();
		
		return ResponseEntity.ok(orders);
	}

	/*
	 * 1 Chờ xác nhận 
	 * 2 Xác nhận đơn hàng 
	 * 3 Chờ lấy hàng 
	 * 4 Lấy hàng thành công 
	 * 5 Lấy
	 * hàng không thành công 
	 * 6 Đang giao 
	 * 7 Đã giao 
	 * 8 Giao hàng không thành công 9
	 * Huỷ đơn
	 */
	
	@GetMapping("order-total-per-day")
	public ResponseEntity<?> doGetPrice(){
		try {
			String order = orderRepository.getToTalPricePerMonth("07", "2022", "minhnh");
			return ResponseEntity.ok(DecimalFormatUtil.FormatDecical(order));
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.ok(e.getLocalizedMessage());
		}
		
	
	}
	
}
