package com.deskover.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.model.entity.database.Order;
import com.deskover.model.entity.database.OrderDetail;
import com.deskover.model.entity.database.OrderItem;
import com.deskover.model.entity.database.OrderStatus;
import com.deskover.model.entity.database.PaymentMethods;
import com.deskover.model.entity.database.Product;
import com.deskover.model.entity.database.ShippingMethods;
import com.deskover.model.entity.database.StatusPayment;
import com.deskover.model.entity.database.UserAddress;
import com.deskover.model.entity.database.Users;
import com.deskover.model.entity.database.repository.OrderDetailRepository;
import com.deskover.model.entity.database.repository.OrderItemRepository;
import com.deskover.model.entity.database.repository.OrderRepository;
import com.deskover.model.entity.database.repository.OrderStatusRepository;
import com.deskover.model.entity.database.repository.PaymentRepository;
import com.deskover.model.entity.database.repository.ProductRepository;
import com.deskover.model.entity.database.repository.ShippingRepository;
import com.deskover.model.entity.database.repository.StatusPaymentRepository;
import com.deskover.model.entity.database.repository.UserRepository;
import com.deskover.model.entity.dto.ProductDto;
import com.deskover.other.util.OrderNumberUtil;
import com.deskover.service.CheckoutService;
import com.deskover.service.SessionService;


@Service
public class CheckoutServiceImpl implements CheckoutService {
	@Autowired  OrderDetailRepository  addressRepo ;
	@Autowired  StatusPaymentRepository  statusPaymentRepo ;
	@Autowired OrderRepository  orderRepo ;
	@Autowired OrderItemRepository  orderItemRepo ;
	@Autowired UserRepository userRepo   ;
	@Autowired ProductRepository productRepo;
	@Autowired ShippingRepository shippingRepo;
	@Autowired PaymentRepository paymentRepo;
	@Autowired OrderStatusRepository statusRepo;
	@Autowired SessionService sessionService;
	
	
	@Override
	public void saveOrder(UserAddress entity, String total) {
		Users user  = userRepo.getById((long)1);
		ShippingMethods shipping = shippingRepo.getById((long)1);
		PaymentMethods payment = paymentRepo.getById((long)1);
		OrderStatus status = statusRepo.getById((long)1);
		StatusPayment statusPayment = statusPaymentRepo.getById((long)1);
		
		ArrayList<ProductDto> items = sessionService.get("items");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		OrderDetail orderAddress = new OrderDetail();
		sessionService.set("address", entity);
		
		//	1 - Save Order
		Order order = new Order();
		order.setOrderCode(OrderNumberUtil.get());
		order.setUser(user);
		order.setShipping(shipping);
		order.setPayment(payment);
		order.setOrderStatus(status);
		order.setStatusPayment(statusPayment);
		order.setFullName(entity.getFullname());
		order.setEmail(entity.getEmail());
		order.setCreatedAt(timestamp);
		order.setUnitPrice(Double.parseDouble(total));
		order.setOrderQuantity(items.size());
		orderRepo.save(order);

		// 2 -  Save Address
		Order lastOrder = orderRepo.getLastOrder();
		orderAddress.setOrder(lastOrder);
		orderAddress.setAddress(entity.getAddress());
		orderAddress.setProvince("1");
		orderAddress.setDistrict("1");
		orderAddress.setWard("1");
		orderAddress.setTel(entity.getTel());
		addressRepo.save(orderAddress);  
		
		//	 3 - Save Items
		for (int i = 0; i < items.size(); i++) {
			Product product = productRepo.getAllByName(items.get(i).getName());
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(product);
			orderItem.setQuantity(items.get(i).getQuantity().intValue());
			orderItem.setPrice(product.getPrice());
			orderItemRepo.save(orderItem);
		}
		System.out.println("Save Succesfull!");
	}
}
