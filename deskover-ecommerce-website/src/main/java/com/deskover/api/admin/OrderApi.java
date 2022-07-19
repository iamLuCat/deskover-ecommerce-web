package com.deskover.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.dto.app.order.OrderDto;
import com.deskover.dto.app.order.resquest.DataOrderResquest;
import com.deskover.dto.app.total7dayago.DataTotaPrice7DaysAgo;
import com.deskover.entity.Order;
import com.deskover.repository.OrderRepository;
import com.deskover.service.OrderService;
import com.deskover.util.DecimalFormatUtil;
import com.deskover.util.QrCodeUtil;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin")
public class OrderApi {
	@Autowired
	private OrderService orderService;
	
	@Autowired OrderRepository orderRepository;
	
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

	// Order status
	@GetMapping("/order")
	public ResponseEntity<?> doGetOrderStatus(@RequestParam("status") String status) {
		List<Order> orders = orderService.getAllOrderStatus(status.toUpperCase());
		if (orders.isEmpty()) {
			ResponseEntity.notFound().build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Không tìm thấy đơn hàng"));
		}
		return ResponseEntity.ok(orders);
	}
	
	@GetMapping("/order/{orderCode}")
	public ResponseEntity<?> doGetOrderByOrderCode(@PathVariable("orderCode") String orderCode,
					@RequestParam("status") String status){
		try {
			if(status.isBlank()) {
				OrderDto orderDto = orderService.findByCode(orderCode);
				return ResponseEntity.ok(orderDto);
			}
			OrderDto orderDto = orderService.findByOrderCode(orderCode, status);
			return ResponseEntity.ok(orderDto);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy đơn hàng"));
		}
	}
	
	@GetMapping("/order/delivery")
	public ResponseEntity<?> doGetDelivery(@RequestParam("status") String status){
		try {
			DataOrderResquest dtos = orderService.getListOrder(status);
			return ResponseEntity.ok(dtos);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy đơn hàng"));
		}
	}
	
	@GetMapping("/order/statis")
	public ResponseEntity<?> doGetAllByUser(){
		try {
			DataOrderResquest dtos = orderService.getListOrderByUser();
			return ResponseEntity.ok(dtos);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy đơn hàng"));
		}
	}

	@GetMapping("/order-7days")
	public ResponseEntity<?> doGetTotalPrice7DaysAgo(){
			try {
				DataTotaPrice7DaysAgo totals = orderService.doGetTotalPrice7DaysAgo();
				return ResponseEntity.ok(totals);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(), e);
			}
    } 
	
	@GetMapping("/order-total-per-month")
	public ResponseEntity<?> doGetPrice() {
		try {
			System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
			String order = orderService.getToTalPricePerMonth();
			return ResponseEntity.ok(DecimalFormatUtil.FormatDecical(order));

		} catch (Exception e) {
			return ResponseEntity.ok("0");
		}
	}
	
	@GetMapping("/order-count-order-per-month")
	public ResponseEntity<?> doGetCountOrder(){
		try {
			String countOrder = orderService.getCountOrderPerMonth();
			return ResponseEntity.ok(DecimalFormatUtil.FormatDecical(countOrder));
			
		} catch (Exception e) {
			return ResponseEntity.ok("0");
		}
	}
	
	@PostMapping("/order/{orderCode}")
	public ResponseEntity<?> doPostPickup(@PathVariable("orderCode") String orderCode,@RequestParam("status") String status,
			@RequestParam("note") String note){
		try {
			 orderService.pickupOrder(orderCode,status,note);
			 return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Cập nhập đơn hàng thất bại"));
		}
	}
	
	@PostMapping("order/manager/{orderCode}")
	public ResponseEntity<?> test(@PathVariable("orderCode") String orderCode){
		try {
			 return ResponseEntity.ok(orderService.managerOrder(orderCode));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Cập nhập đơn hàng thất bại"));
		}
	}
	

}
