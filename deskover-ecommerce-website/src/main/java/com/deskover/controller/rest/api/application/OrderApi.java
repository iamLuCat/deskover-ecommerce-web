package com.deskover.controller.rest.api.application;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.model.entity.database.Order;
import com.deskover.model.entity.dto.security.payload.MessageResponse;
import com.deskover.other.util.MessageErrorUtil;
import com.deskover.other.util.ValidationUtil;
import com.deskover.service.OrderService;

@RestController("OrderApiForClient")
@RequestMapping("v1/api/customer")
public class OrderApi {
	@Autowired
	private OrderService orderService;
	
	// Tìm đơn hàng theo statusCode.
	@GetMapping("/order")
	public ResponseEntity<?> doGetAllByUser(@RequestParam("statusCode") String statusCode){
		try {
			if(statusCode.isBlank()) {
				List<Order> order = orderService.getAllByUser();
				return ResponseEntity.ok(order);	
			}
			List<Order> order = orderService.findByStatusCode(statusCode);
			return ResponseEntity.ok(order);	
		} catch (Exception e) {
            return ResponseEntity.badRequest().body("Không tìm thấy đơn hàng ");
		}
	
	}
	
	@GetMapping("/order/{orderCode}")
	public ResponseEntity<?> doGetByUser(@PathVariable("orderCode") String orderCode){
		try {
			Order order = orderService.finByOrderCodeAndUsername(orderCode);
			return ResponseEntity.ok(order);	
		} catch (Exception e) {
            return ResponseEntity.badRequest().body("Không tìm thấy đơn hàng "+orderCode);
		}
	
	}
	
	@PostMapping("/order")
	public ResponseEntity<?> doPostAdd(@Valid @RequestBody Order oderResponse, BindingResult result){
		if(result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrorsApp(result);
	        return ResponseEntity.badRequest().body(errors);
		}
		try {
			
			Order order = orderService.addOrder(oderResponse);
			return ResponseEntity.ok(new MessageResponse("Đặt hàng thành công\nMã đơn hàng của bạn là:"+order.getOrderCode()));	
		} catch (Exception e) {
            MessageResponse error = MessageErrorUtil.message("Đặt hàng thất bại", e);
            return ResponseEntity.badRequest().body(error);
		}
	
	}
	
	@PostMapping("/order/cancel/{orderCode}")
	public ResponseEntity<?> doPostCancelOrder(@PathVariable("orderCode") String orderCode, 
			@RequestParam("statusOrder") String statusOrder){
		try {
			if(statusOrder.equals("C-HUY")){
				 orderService.cancelOrderByUserAndOrderCode(orderCode,statusOrder);
				return ResponseEntity.ok(new MessageResponse("Đơn hàng của bạn trạng thái chờ huỷ"));
			} 
			if (statusOrder.equals("CANCEL-C-HUY")) {
				orderService.cancelOrderByUserAndOrderCode(orderCode,statusOrder);
				return ResponseEntity.ok(new MessageResponse("Cập nhập đơn hàng thành công"));
			}
			return ResponseEntity.ok(new MessageResponse("Đơn hàng sai trạng thái"));		
		} catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
		}
	}
	
	@PostMapping("/order/cancel1/{orderCode}")
	public ResponseEntity<?> doPostCancelOrder1(@PathVariable("orderCode") String orderCode, 
			@RequestParam("statusOrder") String statusOrder){
		try {
			if(statusOrder.equals("C-HUY")){
				 orderService.cancelOrderByUserAndOrderCode(orderCode,statusOrder);
				return ResponseEntity.ok(new MessageResponse("Đơn hàng của bạn trạng thái chờ huỷ"));
			} 
			if (statusOrder.equals("CANCEL-C-HUY")) {
				orderService.cancelOrderByUserAndOrderCode(orderCode,statusOrder);
				return ResponseEntity.ok(new MessageResponse("Cập nhập đơn hàng thành công"));
			}
			return ResponseEntity.ok(new MessageResponse("Đơn hàng sai trạng thái"));		
		} catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
		}
	
	}

}
