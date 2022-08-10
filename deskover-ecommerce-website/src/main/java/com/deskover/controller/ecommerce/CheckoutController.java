package com.deskover.controller.ecommerce;


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

import com.deskover.model.entity.database.Order;
import com.deskover.model.entity.database.OrderDetail;
import com.deskover.model.entity.database.OrderItem;
import com.deskover.model.entity.database.OrderStatus;
import com.deskover.model.entity.database.PaymentMethods;
import com.deskover.model.entity.database.Product;
import com.deskover.model.entity.database.ShippingMethods;
import com.deskover.model.entity.database.UserAddress;
import com.deskover.model.entity.database.Users;
import com.deskover.model.entity.database.repository.OrderDetailRepository;
import com.deskover.model.entity.database.repository.OrderItemRepository;
import com.deskover.model.entity.database.repository.OrderRepository;
import com.deskover.model.entity.database.repository.OrderStatusRepository;
import com.deskover.model.entity.database.repository.PaymentRepository;
import com.deskover.model.entity.database.repository.ProductRepository;
import com.deskover.model.entity.database.repository.ShippingRepository;
import com.deskover.model.entity.database.repository.UserRepository;
import com.deskover.model.entity.dto.ProductDto;
import com.deskover.other.util.OrderNumberUtil;
import com.deskover.service.SessionService;
import com.deskover.service.ShopService;



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
	@Autowired OrderNumberUtil orderCode;
	@Autowired SessionService sessionService;

	@GetMapping("checkout")
	public String checkout(Model model) {
		return "checkout";
	}
	
	@PostMapping("/amounts")
	public String amounts(@RequestBody List<Integer> amounts, Model model) {
		sessionService.set("amount", amounts);	
		return "ok";
	}
	
	@PostMapping("/checkout")
	public String checkout2(@RequestBody List<ProductDto> items, Model model) {
		try {
			List<Integer> amounts =  sessionService.get("amount");
			for (int i = 0; i < amounts.size(); i++) {
				items.get(i).setQuantity((long)amounts.get(i));
				sessionService.set("items", items);
			}
		} catch (Exception e) { }
		return "ok";
	}

	@PostMapping("/ok")
	public String checkout1(Model model ,@ModelAttribute("addressForm") @Valid UserAddress entity, Errors errors, @ModelAttribute("Total") String total ) {
		ArrayList<ProductDto> items = sessionService.get("items");
		Users user  = userRepo.getById((long)1);
		ShippingMethods shipping = shippingRepo.getById((long)1);
		PaymentMethods payment = paymentRepo.getById((long)1);
		OrderStatus status = statusRepo.getById((long)1);
		Order order1 = new Order();
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		OrderDetail orderAddress = new OrderDetail();
		sessionService.set("address", entity);

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
		return "ok";
	}
	

}
