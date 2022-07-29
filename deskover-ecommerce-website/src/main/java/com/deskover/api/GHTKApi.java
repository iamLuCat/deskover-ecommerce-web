package com.deskover.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.constant.UrlConstant;
import com.deskover.dto.ghtk.entity.FeeGhtk;
import com.deskover.dto.ghtk.entity.OrderGhtk;
import com.deskover.dto.ghtk.entity.ProductsGhtk;
import com.deskover.dto.ghtk.response.FeeResponseData;
import com.deskover.dto.ghtk.response.MessageResponseGhtk;
import com.deskover.dto.ghtk.response.OrderResponseData;
import com.deskover.dto.ghtk.resquest.OrderShippingResquest;
import com.deskover.entity.Order;
import com.deskover.repository.OrderRepository;
import com.deskover.service.GHTKService;
import com.deskover.util.MapperUtil;
import com.deskover.util.ValidationUtil;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/ghtk")
public class GHTKApi {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private GHTKService ghtkService;

	@Autowired
	private OrderRepository orderRepository;

	// api tính phí phận chuyển
	@PostMapping(path = "/fee", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> doGetFee(@RequestBody FeeGhtk fee, @RequestHeader(value = "Token") String Token)
			throws Exception {
		try {
			FeeResponseData response = ghtkService.doGetFee(fee, Token);
			if (Objects.requireNonNull(response).getFee() == null) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("Token", Token);
				HttpEntity<FeeGhtk> request = new HttpEntity<>(fee, headers);
				ResponseEntity<String> errorRequest = restTemplate.postForEntity(UrlConstant.GHTK_FEE, request,
						String.class);
				return ResponseEntity.badRequest().body(errorRequest.getBody());
			}
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	// Doanh sách địa chỉ lấy đơn hàng
	@GetMapping(path = "/shipment/list_address", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> doGetListAddress(@RequestHeader(value = "Token") String Token) throws Exception {

		return ResponseEntity.ok(ghtkService.doGetAddress(Token));
	}

	@GetMapping("/shipment/v2")
	public ResponseEntity<?> doGetAllStatus(@RequestHeader(value = "Token") String Token) {
		try {
			ghtkService.loadOrderStatus(Token);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}

	}
	// Không test api này nhé.

	// api đăng đơn hàng

	@PostMapping("/shipment/order")
	public ResponseEntity<?> doPost(@Valid @RequestBody Order order, BindingResult result,
			@RequestHeader(value = "Token") String Token) throws Exception {
		if (result.hasErrors()) {
            MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
	
		return ResponseEntity.ok(ghtkService.ShipmentOrder(order, Token));

	}

	// label_id: mã vận đơn của giao hàng tiết kiệm
	@PostMapping(path = "/shipment/cancel/{label_id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> doGetCancelShipping(@PathVariable("label_id") String labelId,
			@RequestHeader(value = "Token") String token) throws Exception{
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Token", token);
			HttpEntity<?> header = new HttpEntity<>(headers);
			MessageResponseGhtk response = restTemplate.postForObject(UrlConstant.GHTK_CANCEL + labelId, header,
					MessageResponseGhtk.class);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy đơn hàng"));
		}
	}

}
