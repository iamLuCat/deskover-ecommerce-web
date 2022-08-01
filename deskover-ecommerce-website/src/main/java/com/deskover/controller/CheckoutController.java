package com.deskover.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.deskover.dto.ProductDto;
import com.deskover.entity.Order;
import com.deskover.entity.OrderDetail;
import com.deskover.entity.OrderItem;
import com.deskover.entity.OrderStatus;
import com.deskover.entity.PaymentMethods;
import com.deskover.entity.Product;
import com.deskover.entity.ShippingMethods;
import com.deskover.entity.User;
import com.deskover.entity.UserAddress;
import com.deskover.repository.OrderDetailRepository;
import com.deskover.repository.OrderItemRepository;
import com.deskover.repository.OrderRepository;
import com.deskover.repository.OrderStatusRepository;
import com.deskover.repository.PaymentRepository;
import com.deskover.repository.ProductRepository;
import com.deskover.repository.ShippingRepository;
import com.deskover.repository.UserRepository;
import com.deskover.service.SessionService;
import com.deskover.service.ShopService;
import com.deskover.util.OrderNumberUtil;



@Controller
public class CheckoutController {
	@Autowired OrderDetailRepository  addressRepo ;
	@Autowired OrderRepository  orderRepo ;
	@Autowired OrderItemRepository  orderItemRepo ;
	@Autowired UserRepository userRepo   ;
	@Autowired ShopService shopService;
	@Autowired ProductRepository productRepo;
	@Autowired ShippingRepository shippingRepo;
	@Autowired PaymentRepository paymentRepo;
	@Autowired OrderStatusRepository statusRepo;
	
	@GetMapping("checkout")
	public String checkout(Model model) {
		return "checkout";
	}
	
	@Autowired SessionService sessionService;
	
	@PostMapping("/amounts")
	public String amounts(@RequestBody List<Integer> amounts, Model model) {
		sessionService.set("amount", amounts);	
		return "ok";
	}
	
	@PostMapping("/checkout")
	public String checkout2(@RequestBody List<ProductDto> items, Model model) {
		List<Integer> amounts =  sessionService.get("amount");
		for (int i = 0; i < amounts.size(); i++) {
			items.get(i).setQuantity((long)amounts.get(i));
		}
		sessionService.set("items", items);
		return "ok";
	}
	
	@Autowired OrderNumberUtil orderCode;

	@PostMapping("/ok")
	public String checkout1(@ModelAttribute("addressForm") @Valid UserAddress entity, Errors errors, @ModelAttribute("Total") String total ) {
		ArrayList<ProductDto> items = sessionService.get("items");
		User user  = userRepo.getById((long)1);
		ShippingMethods shipping = shippingRepo.getById((long)1);
		PaymentMethods payment = paymentRepo.getById((long)1);
		OrderStatus status = statusRepo.getById((long)1);
		Order order1 = new Order();
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		OrderDetail orderAddress = new OrderDetail();

		
		//	1 - Save Order
		order1.setOrderCode(orderCode.get());
		order1.setUser(user);
		order1.setShipping(shipping);
		order1.setPayment(payment);
		order1.setOrderStatus(status);
		order1.setFullName(user.getFullname());
		order1.setCreatedAt(timestamp);
		order1.setUnitPrice(Double.parseDouble(total));
		order1.setOrderQuantity(items.size());
		orderRepo.save(order1);

		// 2 -  Save Address
		Order order = orderRepo.getlastOrder();
		orderAddress.setOrder(order);
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
		return "ok";
	}
	
}
