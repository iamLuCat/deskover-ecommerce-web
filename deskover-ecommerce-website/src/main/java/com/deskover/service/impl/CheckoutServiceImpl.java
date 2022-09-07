package com.deskover.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.dto.ProductDto;
import com.deskover.entity.Order;
import com.deskover.entity.OrderDetail;
import com.deskover.entity.OrderItem;
import com.deskover.entity.OrderStatus;
import com.deskover.entity.PaymentMethods;
import com.deskover.entity.Product;
import com.deskover.entity.ShippingMethods;
import com.deskover.entity.StatusPayment;
import com.deskover.entity.UserAddress;
import com.deskover.entity.Users;
import com.deskover.reponsitory.OrderDetailRepository;
import com.deskover.reponsitory.OrderItemRepository;
import com.deskover.reponsitory.OrderRepository;
import com.deskover.reponsitory.OrderStatusRepository;
import com.deskover.reponsitory.PaymentRepository;
import com.deskover.reponsitory.ProductRepository;
import com.deskover.reponsitory.ShippingRepository;
import com.deskover.reponsitory.StatusPaymentRepository;
import com.deskover.reponsitory.UserRepository;
import com.deskover.service.CheckoutService;
import com.deskover.service.SessionService;
import com.deskover.utils.OrderNumberUtil;


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
	public void saveOrder(UserAddress entity, String total, ArrayList<ProductDto> items) {
		Users user  = userRepo.getById((long)1);
		ShippingMethods shipping = shippingRepo.getById((long)1);
		PaymentMethods payment = paymentRepo.getById((long)1);
		OrderStatus status = statusRepo.getById((long)1);
		StatusPayment statusPayment = statusPaymentRepo.getById((long)1);
		
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
		System.out.println(entity.getProvince());
		orderAddress.setAddress(entity.getAddress());

		orderAddress.setProvince(entity.getProvince().substring(7));
		orderAddress.setDistrict(entity.getDistrict().substring(7));
		orderAddress.setWard(entity.getWard().substring(7));
		orderAddress.setTel(entity.getTel());
		sessionService.set("status", "Thanh toán trực tiếp bằng hình thức COD");
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
	
	@Override
	public void saveOrderPay(UserAddress entity, String total, ArrayList<ProductDto> items) {
		Users user  = userRepo.getById((long)1);
		ShippingMethods shipping = shippingRepo.getById((long)1);
		PaymentMethods payment = paymentRepo.getById((long)1);
		OrderStatus status = statusRepo.getById((long)1);
		StatusPayment statusPayment = statusPaymentRepo.getById((long)2);
		
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
		sessionService.set("status", "Thanh toán qua thẻ ATM bằng ví điện tử VNPAY");
		orderRepo.save(order);

		// 2 -  Save Address
		Order lastOrder = orderRepo.getLastOrder();
		orderAddress.setOrder(lastOrder);
		orderAddress.setAddress(entity.getAddress());
		orderAddress.setProvince(entity.getProvince());
		orderAddress.setDistrict(entity.getDistrict());
		orderAddress.setWard(entity.getWard());
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
