package com.deskover.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.entity.Order;
import com.deskover.repository.OrderRepository;
import com.deskover.service.OrderService;
import com.deskover.util.DecimalFormatUtil;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin")
public class OrderApi {
	
	
		
	@Autowired
	private OrderService orderService;
	
	@Autowired OrderRepository orderRepository;
	
	@GetMapping("/order/all")
	public ResponseEntity<?> doGetAll(){
		List<Order> orders = orderService.getAll();
		
		return ResponseEntity.ok(orders);
	}

	/*
	 * 1 Chờ xác nhận 
	 * 2 Xác nhận đơn hàng 
	 * 3 Chờ lấy hàng 
	 * 4 Lấy hàng thành công 
	 * 5 Lấy hàng không thành công
	 * 6 Đang giao 
	 * 7 Đã giao 
	 * 8 Giao hàng không thành công 
	 * 9.Huỷ đơn
	 */
	
	//Order status
	@GetMapping("/order")
	public ResponseEntity<?> doGetOrderStatus(@RequestParam("status") String status){
		List<Order> orders = orderService.getAllOrderStatus(status.toUpperCase());
		if(orders.isEmpty()) {
			ResponseEntity.notFound().build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Không tìm thấy đơn hàng"));
		}
		return ResponseEntity.ok(orders);
	}
	
	@GetMapping("order/{orderCode}")
	public ResponseEntity<?> doGetOrderByOrderCode(@PathVariable("orderCode") String orderCode,
					@RequestParam("status") String status
			){
		
		try {
			Order order = orderService.findByOrderCode(orderCode, status.toUpperCase());
			return ResponseEntity.ok(order);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(), e);
		}
	
	}
	
	
	@GetMapping("order-total-per-month")
	public ResponseEntity<?> doGetPrice(@RequestParam("userModified") String userModified){
		try {
			String order = orderService.getToTalPricePerMonth(userModified);
			return ResponseEntity.ok(DecimalFormatUtil.FormatDecical(order));
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.ok(e.getLocalizedMessage());
		}
	}
	
}
