package com.deskover.service.impl;

import java.text.DecimalFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.deskover.dto.OrderDto;
import com.deskover.dto.OrderItemDto;
import com.deskover.entity.Order;
import com.deskover.entity.OrderDetail;
import com.deskover.entity.OrderItem;
import com.deskover.repository.OrderDetailRepository;
import com.deskover.repository.OrderItemRepository;
import com.deskover.repository.OrderRepository;
import com.deskover.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository repository;
	
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;

	@Override
	public List<Order> getAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public List<Order> getAllOrderStatus(String status) {
		return repository.findByOrderStatusCode(status);
	}

	@Override
	public OrderDto findByOrderCode(String orderCode, String status) {
		
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		
		Order order = repository.findByOrderCodeAndOrderStatusCode(orderCode, status);
		if(order == null) {
			throw new IllegalArgumentException("Không tìm thấy sản phẩm");
			
		}
		OrderDto orderDto = mapper.map(order, OrderDto.class);
		OrderDetail orderDetail = orderDetailRepository.findByOrder(order);
		
		orderDto.setAddress(orderDetail.getAddress());
		orderDto.setProvince(orderDetail.getProvince());
		orderDto.setDistrict(orderDetail.getDistrict());
		orderDto.setWard(orderDetail.getWard());
		orderDto.setTel(orderDetail.getTel());
		
		orderDto.setCode(order.getOrderStatus().getCode());
		orderDto.setStatus(order.getOrderStatus().getStatus());
		
		List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
		List<OrderItemDto> itemDtos = new ArrayList<>();
		
		for (OrderItem item : orderItems) {
			OrderItemDto itemDto = new OrderItemDto();
			itemDto.setName(item.getProduct().getName());
			itemDto.setPrice(formatter.format(item.getPrice()));
			itemDto.setQuantity(item.getQuantity());
			itemDtos.add(itemDto);
		}
		orderDto.setOrderItem(itemDtos);
		orderDto.setTotalPrice(formatter.format(repository.getTotalOrder(order.getId())));
		
	
		return orderDto;
	}

	@Override
	public String getToTalPricePerMonth() {
		YearMonth currentTimes = YearMonth.now();
		return repository.getToTalPricePerMonth(currentTimes.getMonthValue()+"",
				currentTimes.getYear()+"",SecurityContextHolder.getContext().getAuthentication().getName());
	
		
	}

	@Override
	public String getCountOrderPerMonth() {
		YearMonth currentTimes = YearMonth.now();
		return repository.getCountOrder(currentTimes.getMonthValue()+"",
				currentTimes.getYear()+"",SecurityContextHolder.getContext().getAuthentication().getName());
	}

}
