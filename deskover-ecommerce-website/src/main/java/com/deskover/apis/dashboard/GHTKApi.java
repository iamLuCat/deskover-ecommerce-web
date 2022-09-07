package com.deskover.apis.dashboard;

import com.deskover.constant.UrlConstant;
import com.deskover.dto.MessageResponse;
import com.deskover.dto.ghtk.FeeGhtk;
import com.deskover.dto.ghtk.response.FeeResponseData;
import com.deskover.dto.ghtk.response.MessageResponseGhtk;
import com.deskover.entity.Order;
import com.deskover.service.GHTKService;
import com.deskover.utils.ValidationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/ghtk")
public class GHTKApi {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private GHTKService ghtkService;

	// api tính phí phận chuyển
	@PostMapping(path = "/fee", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> doGetFee(@RequestBody FeeGhtk fee)
			throws Exception {
		try {
			FeeResponseData response = ghtkService.doGetFee(fee, "2C925D6789957674DcC9121bf419Df1a2F7b0BC3");
			if (Objects.requireNonNull(response).getFee() == null) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("Token",  "2C925D6789957674DcC9121bf419Df1a2F7b0BC3");
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
			@RequestHeader(value = "Token") String token) throws Exception {
		if (result.hasErrors()) {
            MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
	
		return ResponseEntity.ok(ghtkService.shipmentOrder(order, token));

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
