package com.deskover.api;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.constant.UrlConstant;
import com.deskover.dto.ghtk.entity.FeeGhtk;
import com.deskover.dto.ghtk.response.AddressResponseData;
import com.deskover.dto.ghtk.response.FeeResponseData;
import com.deskover.dto.ghtk.response.MessageResponseGhtk;
import com.deskover.entity.Order;
import com.deskover.service.GHTKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@RequestMapping("v1/api/ghtk")
public class GHTKApi {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private GHTKService ghtkService;

	// api tính phí phận chuyển
	@PostMapping(path = "/fee", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> Fee(@RequestBody FeeGhtk fee,@RequestHeader(value="Token") String Token) throws Exception {

		String url = UrlConstant.GHTK_FEE;

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Token", Token);
		try {
			HttpEntity<FeeGhtk> request = new HttpEntity<>(fee, headers);
			FeeResponseData response = restTemplate.postForObject(url, request, FeeResponseData.class);
			if (Objects.requireNonNull(response).getFee() == null) {
				ResponseEntity<String> errorRequest = restTemplate.postForEntity(url, request, String.class);
				return ResponseEntity.ok(errorRequest.getBody());
			}
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	// Doanh sách địa chỉ lấy đơn hàng
	@GetMapping(path = "/shipment/list_address", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> doGetListAddress(@RequestBody AddressResponseData addressResponse,@RequestHeader(value="Token") String Token) throws Exception {

		String url = UrlConstant.GHTK_LIST_ADDRESS;

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Token", Token);

		System.out.println(headers);

		HttpEntity<AddressResponseData> request = new HttpEntity<>(addressResponse, headers);

		AddressResponseData response = restTemplate.postForObject(url, request, AddressResponseData.class);

		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/shipment/v2")
	public ResponseEntity<?> doGetAllStatus(@RequestHeader(value="Token") String Token){
		try {
			ghtkService.loadOrderStatus(Token);
			return new  ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(),e);
		}

	}
	// Không test api này nhé.

	// api đăng đơn hàng
	
	@PostMapping("/shipment/order")
	public ResponseEntity<?> doPost(@RequestBody Order order,@RequestHeader(value="Token") String Token){
		try {
			return ResponseEntity.ok(ghtkService.ShipmentOrder(order, Token));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(),e);
		}

	}

	
	//label_id: mã vận đơn của giao hàng tiết kiệm
	@PostMapping(path = "/shipment/cancel/{label_id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> doGetCancelShipping(@PathVariable("label_id") String labelId,@RequestHeader(value="Token") String token){
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Token", token);

			HttpEntity<?> header = new HttpEntity<>(headers);
			MessageResponseGhtk response = restTemplate.postForObject(UrlConstant.GHTK_CANCEL+ labelId, header, MessageResponseGhtk.class);
			return ResponseEntity.ok(response);
		} catch (Exception e) {		
			return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy đơn hàng"));
		}
	}
	
	

}
