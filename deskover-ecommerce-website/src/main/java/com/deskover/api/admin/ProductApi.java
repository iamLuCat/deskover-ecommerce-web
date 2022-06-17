package com.deskover.api.admin;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.dto.ghtk.UrlGGStrogeResponDto;
import com.deskover.entity.Product;
import com.deskover.repository.ProductRepository;
import com.deskover.service.ProductService;

@RestController
@RequestMapping("v1/api/admin")
public class ProductApi {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/products/actived")
	public ResponseEntity<?> doGetAll(@RequestParam("page") Integer page, @RequestParam("items") Integer items) {
		List<Product> products = productService.findByActived(Boolean.TRUE, page, items);
		if (products.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Products Not Found"));
		}
		return ResponseEntity.ok(products);

	}

	@GetMapping("/products/unactived")
	public ResponseEntity<?> doGetUnActived(@RequestParam("page") Integer page, @RequestParam("items") Integer items) {
		List<Product> products = productService.findByActived(Boolean.FALSE, page, items);
		if (products.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Products UnActive Not Found"));
		}
		return ResponseEntity.ok(products);
	}

//	@PostMapping("/products")
//	public ResponseEntity<?> doPostCreate(@RequestParam("files") MultipartFile files) {
//		
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//		
//		body.add("files", files);
//		
//		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
//		String response = restTemplate.postForObject("http://localhost:8080/v1/api/admin/upload", request, String.class);
////		product.setImage(response.getUrl());
////		
////		Product products = productRepository.saveAndFlush(product);
//		
//		return ResponseEntity.ok(response);
//
//	}

}