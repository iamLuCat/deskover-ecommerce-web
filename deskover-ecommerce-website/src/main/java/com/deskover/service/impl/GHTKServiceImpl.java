package com.deskover.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.deskover.model.entity.database.Order;
import com.deskover.model.entity.database.repository.OrderRepository;
import com.deskover.model.entity.extend.ghtk.FeeGhtk;
import com.deskover.model.entity.extend.ghtk.OrderGhtk;
import com.deskover.model.entity.extend.ghtk.ProductsGhtk;
import com.deskover.model.entity.extend.ghtk.response.AddressResponseData;
import com.deskover.model.entity.extend.ghtk.response.FeeResponseData;
import com.deskover.model.entity.extend.ghtk.response.OrderResponseData;
import com.deskover.model.entity.extend.ghtk.resquest.OrderShippingRequest;
import com.deskover.other.constant.UrlConstant;
import com.deskover.other.util.MapperUtil;
import com.deskover.service.GHTKService;
import com.deskover.service.OrderStatusService;

@Service
public class GHTKServiceImpl implements GHTKService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderStatusService orderStatusService;
	
	@Override
	@Transactional
	public OrderShippingRequest shipmentOrder(Order order, String header) {
		
		List<ProductsGhtk> products = new ArrayList<>();
		order.getProducts().forEach((product)->{
			ProductsGhtk productsGhtk = MapperUtil.map(product, ProductsGhtk.class);
			productsGhtk.setName(product.getProduct().getName());
			productsGhtk.setWeight(product.getProduct().getWeight());
			productsGhtk.setProduct_code("");
			products.add(productsGhtk);
		});
		
		OrderGhtk orderGhtk = MapperUtil.map(order.getOrderDetail(), OrderGhtk.class);
		System.out.println(orderGhtk.getAddress());
			orderGhtk.setId(order.getOrderCode());
			orderGhtk.setPick_name("DESKOVER-DEV");
			orderGhtk.setPick_address("590 CMT8 P.11");
			orderGhtk.setPick_province("TP. Hồ Chí Minh");
			orderGhtk.setPick_district("Quận 3");
			orderGhtk.setPick_ward("Phường 1");
			orderGhtk.setPick_tel("0911222333");
			orderGhtk.setAddress(order.getOrderDetail().getAddress());
			orderGhtk.setProvince(order.getOrderDetail().getProvince());
			orderGhtk.setDistrict(order.getOrderDetail().getDistrict());
			orderGhtk.setWard(order.getOrderDetail().getWard());
			orderGhtk.setPick_money(order.getUnitPrice().intValue() > 19500000 ? 19500000 : order.getUnitPrice().intValue());
			orderGhtk.setValue(order.getUnitPrice().intValue() > 19500000 ? 19500000 : order.getUnitPrice().intValue());
			orderGhtk.setHamlet("Khác");
			orderGhtk.setName(order.getFullName());
		
		OrderResponseData orderResponseData = new OrderResponseData(); 
			orderResponseData.setProducts(products);
			orderResponseData.setOrder(orderGhtk);
		
		HttpHeaders headers = new HttpHeaders();
		
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Token", header);

		HttpEntity<OrderResponseData> request = new HttpEntity<>(orderResponseData, headers);
		OrderShippingRequest response = restTemplate.postForObject(UrlConstant.GHTK_ORDER, request, OrderShippingRequest.class);
		Order orderRepo = orderRepository.getById(order.getId());
			orderRepo.setLabel(response.getOrder().getLabel());
			orderRepo.setFee(response.getOrder().getFee());
			orderRepo.setEstimated_pick_time(response.getOrder().getEstimated_pick_time());
			orderRepo.setEstimated_deliver_time(response.getOrder().getEstimated_deliver_time());

			orderRepository.saveAndFlush(orderRepo);
		return response;
	}

	@Override
	@Transactional
	public void loadOrderStatus(String header) throws Exception {
		List<Order> orders = orderRepository.findByShippingShippingId("GHTK");
		orders.forEach((order)->{
			if(!order.getOrderStatus().getCode().equals("C-XN")) {
				if(order.getLabel() != null) {
					HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						headers.set("Token", header);
					HttpEntity<?> request = new HttpEntity<>(headers);
					OrderShippingRequest response = restTemplate.postForObject(UrlConstant.GHTK_FIND_STATUS+order.getLabel(), request, OrderShippingRequest.class);
					if(response.getOrder().getStatus().equals("2")) {
						order.setOrderStatus(orderStatusService.doGetByStatusCode("C-LH"));
						orderRepository.saveAndFlush(order);
					}else if(response.getOrder().getStatus().equals("3")) {
						order.setOrderStatus(orderStatusService.doGetByStatusCode("LH-TC"));
						orderRepository.saveAndFlush(order);
					}else if (response.getOrder().getStatus().equals("4")) {
						order.setOrderStatus(orderStatusService.doGetByStatusCode("DG"));
						orderRepository.saveAndFlush(order);
					}else if (response.getOrder().getStatus().equals("5")) {
						order.setOrderStatus(orderStatusService.doGetByStatusCode("GH-TC"));
						orderRepository.saveAndFlush(order);
					}
				}
				
			}
		}
		);
		
	}

	@Override
	public FeeResponseData doGetFee(FeeGhtk fee, String token) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Token", token);
		fee.setPick_province("TP. Hồ Chí Minh");
		fee.setPick_district("Quận 3");
		HttpEntity<FeeGhtk> request = new HttpEntity<>(fee, headers);
		FeeResponseData response = restTemplate.postForObject(UrlConstant.GHTK_FEE, request, FeeResponseData.class);
		return response;	
	}

	@Override
	public AddressResponseData doGetAddress(String Token) throws Exception{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Token", Token);
		HttpEntity<AddressResponseData> request = new HttpEntity<>(headers);
		AddressResponseData response = restTemplate.postForObject(UrlConstant.GHTK_LIST_ADDRESS, request, AddressResponseData.class);
		return response;
	}

}
