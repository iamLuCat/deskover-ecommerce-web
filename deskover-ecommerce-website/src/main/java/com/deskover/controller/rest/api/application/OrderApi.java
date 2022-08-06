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
	public ResponseEntity<?> doPostCancelOrder(@PathVariable("orderCode") String orderCode){
		try {
			 orderService.cancelOrderByUserAndOrderCode(orderCode);
			return ResponseEntity.ok(new MessageResponse("Đơn hàng của bạn trạng thái chờ huỷ"));	
		} catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
		}
	
	}

}
