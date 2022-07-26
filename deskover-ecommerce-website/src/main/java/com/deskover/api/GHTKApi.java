package com.deskover.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEvaluationContextExtension.JpaRootObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.constant.UrlConstant;
import com.deskover.dto.ghtk.entity.FeeGhtk;
import com.deskover.dto.ghtk.response.AddressResponseData;
import com.deskover.dto.ghtk.response.FeeResponseData;
import com.deskover.dto.ghtk.response.MessageResponseGhtk;
import com.deskover.dto.ghtk.response.OrderResponseData;
import com.deskover.util.MapperUtil;

import java.util.Objects;

@RestController
@RequestMapping("v1/api/ghtk")
public class GHTKApi {

	@Autowired
	RestTemplate restTemplate;

	// api tính phí phận chuyển
	@PostMapping(path = "/fee", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> Fee(@RequestBody FeeGhtk fee) throws Exception {

		String url = UrlConstant.GHTK_FEE;

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Token", UrlConstant.GHTK_TOKEN);
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
	public ResponseEntity<?> doGetListAddress(@RequestBody AddressResponseData addressResponse) throws Exception {

		String url = UrlConstant.GHTK_LIST_ADDRESS;

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Token", UrlConstant.GHTK_TOKEN);

		System.out.println(headers);

		HttpEntity<AddressResponseData> request = new HttpEntity<>(addressResponse, headers);

		AddressResponseData response = restTemplate.postForObject(url, request, AddressResponseData.class);

		return ResponseEntity.ok(response);
	}

	// Không test api này nhé.

	// api đăng đơn hàng

	@PostMapping(path = "/shipment/order", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> doGetGHTK(@RequestBody OrderResponseData Data) throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Token", UrlConstant.GHTK_TOKEN);

		HttpEntity<OrderResponseData> request = new HttpEntity<>(Data, headers);
		String response = restTemplate.postForObject(UrlConstant.GHTK_ORDER, request, String.class);

		return ResponseEntity.ok(response);
	}
	
	//label_id: mã vận đơn của giao hàng tiết kiệm
	@PostMapping(path = "/shipment/cancel/{label_id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> doGetCancelShipping(@PathVariable("label_id") String label_id){
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Token", UrlConstant.GHTK_TOKEN);

			HttpEntity<?> header = new HttpEntity<>(headers);
			MessageResponseGhtk response = restTemplate.postForObject(UrlConstant.GHTK_CANCEL+label_id, header, MessageResponseGhtk.class);
			return ResponseEntity.ok(response);
		} catch (Exception e) {		
			return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy đơn hàng"));
		}
	}
	
	

}
