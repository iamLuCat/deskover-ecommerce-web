package com.deskover.service.impl;

import com.deskover.dto.app.order.OrderDto;
import com.deskover.dto.app.order.OrderItemDto;
import com.deskover.dto.app.order.resquest.DataOrderResquest;
import com.deskover.dto.app.total7dayago.DataTotaPrice7DaysAgo;
import com.deskover.dto.app.total7dayago.Total7DaysAgo;
import com.deskover.entity.*;
import com.deskover.repository.*;
import com.deskover.repository.datatables.OrderRepoForDatatables;
import com.deskover.service.CartService;
import com.deskover.service.OrderService;
import com.deskover.service.UserAddressService;
import com.deskover.util.DecimalFormatUtil;
import com.deskover.util.MapperUtil;
import com.deskover.util.QrCodeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository repository;

	@Autowired
	private OrderRepoForDatatables repoForDatatables;
	
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private OrderStatusReponsitory orderStatusReponsitory;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private UserAddressService addressService;
    


	@Override
	public List<Order> getAll() {
		return repository.findAll();
	}

	@Override
	public DataTablesOutput<Order> getAllForDatatables(@Valid DataTablesInput input, String statusCode) {
		DataTablesOutput<Order> orders = repoForDatatables.findAll(input, (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (statusCode != null && !statusCode.isBlank()) {
				predicates.add(criteriaBuilder.equal(root.get("orderStatus").get("code"), statusCode));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		});
		if (orders.getError() != null) {
			throw new IllegalArgumentException(orders.getError());
		}
		return orders;
	}

	@Override
	public List<Order> getAllOrderStatus(String status) {
		return repository.findByOrderStatusCode(status);
	}
	
	@Override
	public DataTotaPrice7DaysAgo doGetTotalPrice7DaysAgo() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDateTime now = LocalDateTime.now();
		List<Total7DaysAgo> total7DaysAgos = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			LocalDateTime then = now.minusDays(i);
			Total7DaysAgo day = new Total7DaysAgo();
			day.setDate(then.format(format));
			String total = repository.getTotalPrice_Shipping_PerDay(then.getDayOfMonth()+"",
					then.getMonth().getValue()+"",
					then.getYear()+"",
					SecurityContextHolder.getContext().getAuthentication().getName(), "GH-TC" );
			if(total != null) {
				day.setTotalPrice(Double.parseDouble(total));
				day.setPriceFormat(DecimalFormatUtil.FormatDecical(total)+"đ");				
				System.out.println(day.getTotalPrice());
			}else {
				day.setTotalPrice(0.0);
				day.setPriceFormat("0.0đ");
			}
			
			total7DaysAgos.add(day);

		}
		DataTotaPrice7DaysAgo totals = new DataTotaPrice7DaysAgo();
		totals.setData(total7DaysAgos);
		return totals;
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
			itemDto.setImg(item.getProduct().getImg());
			itemDtos.add(itemDto);
		}
		orderDto.setOrderItem(itemDtos);
		orderDto.setTotalPrice(formatter.format(repository.getTotalOrder(order.getId())));
		
	
		return orderDto;
	}
	
	@Override
	public DataOrderResquest getListOrder(String status) {
		
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		
		List<Order> orders = repository.findByModifiedByAndOrderStatusCode(SecurityContextHolder.getContext().getAuthentication().getName(),status);
		if(orders == null) {
			throw new IllegalArgumentException("Không tìm thấy sản phẩm");
			
		}
		List<OrderDto> orderDtos = new ArrayList<>();
		
		orders.forEach(order->{
			OrderDto orderDto =  mapper.map(order, OrderDto.class);
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
				itemDto.setImg(item.getProduct().getImg());
				itemDtos.add(itemDto);
			}
			orderDto.setOrderItem(itemDtos);
			orderDto.setTotalPrice(formatter.format(repository.getTotalOrder(order.getId())));
			orderDtos.add(orderDto);
		});
		DataOrderResquest data = new DataOrderResquest();
		data.setData(orderDtos);
		
		return data;
	}
	
	@Override
	public DataOrderResquest getListOrderByUser() {
		
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		
		List<Order> orders = repository.findByModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		if(orders == null) {
			throw new IllegalArgumentException("Không tìm thấy sản phẩm");
			
		}
		List<OrderDto> orderDtos = new ArrayList<>();
		
		orders.forEach(order->{
			OrderDto orderDto =  mapper.map(order, OrderDto.class);
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
				itemDto.setImg(item.getProduct().getImg());
				itemDtos.add(itemDto);
			}
			orderDto.setOrderItem(itemDtos);
			orderDto.setTotalPrice(formatter.format(repository.getTotalOrder(order.getId())));
			orderDtos.add(orderDto);
		});
		DataOrderResquest data = new DataOrderResquest();
		data.setData(orderDtos);
		
		return data;
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

	@Override
	@Transactional
	public void pickupOrder(String orderCode, String code,String note) {
		try {
			Order order = repository.findByOrderCode(orderCode);
			if(order == null) {
				throw new IllegalArgumentException("Không tìm thấy sản phẩm");
				
			}
			OrderStatus status = orderStatusReponsitory.findByCode(code);
			if(status == null) {
				throw new IllegalArgumentException("Cập nhập thất bại");
				
			}
			order.setShipping_note(note);
			order.setOrderStatus(status);
			order.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			repository.saveAndFlush(order);
		} catch (Exception e) {
			throw new IllegalArgumentException("Cập nhập đơn hàng thấy bại");
		}
		
	}

	@Override
	public OrderDto findByCode(String orderCode) {
	DecimalFormat formatter = new DecimalFormat("###,###,###");
		
		Order order = repository.findByOrderCode(orderCode);
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
			itemDto.setImg(item.getProduct().getImg());
			itemDtos.add(itemDto);
		}
		orderDto.setOrderItem(itemDtos);
		orderDto.setTotalPrice(formatter.format(repository.getTotalOrder(order.getId())));
		return orderDto;
	}

	@Override
	public Order managerOrder(String orderCode) {
		Order order = repository.findByOrderCode(orderCode);
		if(order==null) {
			throw new IllegalArgumentException("Không tìm thấy đơn hàng");
		}
		if(order.getOrderStatus().getCode().equals("C-XN")) {
			order.setQrCode(QrCodeUtil.QrCode(order.getOrderCode(), order.getOrderCode()));
			order.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			order.setOrderStatus(orderStatusReponsitory.findByCode("C-LH"));
			return repository.saveAndFlush(order);
		}else if (order.getOrderStatus().getCode().equals("C-HUY")) {
			order.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			order.setOrderStatus(orderStatusReponsitory.findByCode("HUY"));
			return repository.saveAndFlush(order);
		}
		throw new IllegalArgumentException("Đơn hàng code_status = 'C-XN' hoặc 'C-HUY'!!");
	}

	@Override
	@Transactional
	public void addOrder(Order orderResponse, String username) {
		
		System.out.println(orderResponse.getFullName());
		
		Order order = mapper.map(orderResponse, Order.class);
			order.setOrderCode("HD-12321");
			order.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		Order orderNew = repository.saveAndFlush(order);
		
		List<Cart> cartItem = cartService.doGetAllCartOrder(username);
		if(cartItem.isEmpty()) {
			throw new IllegalArgumentException("Giỏ hàng trống");
		}
		cartRepository.deleteAll(cartItem);
		List<OrderItem> orderItems = MapperUtil.mapAll(cartItem, OrderItem.class);
		orderItems.forEach((item)->{
			item.setId(null);
			item.setPrice(item.getProduct().getPrice());
			item.setOrder(orderNew);
			orderItemRepository.saveAndFlush(item);
		});
		UserAddress address = addressService.findByUsernameAndChoose(username, Boolean.TRUE);
		OrderDetail orderDetail = mapper.map(address, OrderDetail.class);
		orderDetail.setId(null);
		orderDetail.setOrder(orderNew);
		orderDetailRepository.saveAndFlush(orderDetail);
	}

	@Override
	public Boolean isUniqueOrderNumber(String orderNumber) {
		return Objects.isNull(repository.findByOrderCode(orderNumber));
	}

}
