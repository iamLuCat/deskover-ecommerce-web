package com.deskover.api.app;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.configuration.security.payload.response.MessageErrorUtil;
import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.entity.Order;
import com.deskover.service.OrderService;
import com.deskover.util.ValidationUtil;

@RestController("OrderApiForClient")
@RequestMapping("v1/api/customer")
public class OrderApi {
	
	@Autowired
	private OrderService orderService;
	
	
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
            MessageResponse error = MessageErrorUtil.message("Thêm mới thất bại", e);
            return ResponseEntity.badRequest().body(error);
		}
	
	}

}
