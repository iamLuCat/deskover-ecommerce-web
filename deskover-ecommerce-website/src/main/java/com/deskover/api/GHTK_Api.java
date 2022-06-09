package com.deskover.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.deskover.constants.UrlConstant;
import com.deskover.dto.GHTKDto.entity.FeeGhtk;
import com.deskover.dto.GHTKDto.reponse.AddressReponseData;
import com.deskover.dto.GHTKDto.reponse.FeeReponseData;

import java.util.Objects;

@RestController
@RequestMapping("v1/api/ghtk")
public class GHTK_Api {
	
	@Autowired
	RestTemplate restTemplate;
	
	
	/**
	 * @param fee the fee of GHTK
	 * @return the fee
	 * @throws Exception if an error occurs
	 */
	@PostMapping(path = "/fee", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> Fee(@RequestBody FeeGhtk fee) throws Exception {

		String url = UrlConstant.GHTK_FEE;

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Token", UrlConstant.GHTK_TOKEN);
		try {
			HttpEntity<FeeGhtk> request = new HttpEntity<>(fee, headers);
			FeeReponseData response = restTemplate.postForObject(url, request, FeeReponseData.class);
			if(Objects.requireNonNull(response).getFee() == null) {
				ResponseEntity<String> errorRequest = restTemplate.postForEntity(url, request, String.class);
				return ResponseEntity.ok(errorRequest.getBody());
			}
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
		    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * @param addressResponse the address of GHTK
	 * @return the address
	 * @throws Exception if an error occurs
	 */
	@GetMapping(path = "/shipment/list_address", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> doGetListAddress(@RequestBody AddressReponseData  addressResponse) throws Exception {

		String url = UrlConstant.GHTK_LIST_ADDRESS; 

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Token", UrlConstant.GHTK_TOKEN);
		
		System.out.println(headers);
		
		HttpEntity<AddressReponseData> request = new HttpEntity<>(addressResponse, headers);
		
		AddressReponseData response = restTemplate.postForObject(url, request, AddressReponseData.class);

		return ResponseEntity.ok(response);
	}

	//Không test api này nhé.
	
	/*
	 * @PostMapping(path= "/shipment/order", consumes = "application/json", produces
	 * = "application/json") public ResponseEntity<?> doGetGHTK(@RequestBody
	 * DataGhtk Data) throws Exception{
	 * 
	 * String url = UrlConstant.GHTK_ORDER; String token =
	 * "2C925D6789957674DcC9121bf419Df1a2F7b0BC3";
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setContentType(MediaType.APPLICATION_JSON); headers.set("Token",
	 * token);
	 * 
	 * HttpEntity<DataGhtk> request = new HttpEntity<>(Data, headers); DataGhtk
	 * response = restTemplate.postForObject(url,request, DataGhtk.class); return
	 * ResponseEntity.ok(response); }
	 */

	
}
