package com.deskover.service.impl;

import com.deskover.model.entity.database.*;
import com.deskover.model.entity.database.repository.*;
import com.deskover.model.entity.database.repository.datatable.OrderRepoForDatatables;
import com.deskover.model.entity.dto.application.*;
import com.deskover.other.util.DecimalFormatUtil;
import com.deskover.other.util.MapperUtil;
import com.deskover.other.util.OrderNumberUtil;
import com.deskover.other.util.QrCodeUtil;
import com.deskover.service.*;
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
	private OrderRepository repo;

	@Autowired
	private OrderRepoForDatatables repoForDatatables;

	@Autowired
	private OrderDetailRepository orderDetailRepo;

	@Autowired
	private OrderItemRepository orderItemRepo;

	@Autowired
	private OrderStatusRepository orderStatusRepo;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PaymentService paymentService;

	@Autowired
	private ShippingService shippingService;

	@Autowired
	private CartService cartService;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserAddressService addressService;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ProductService productService;

	@Autowired
	private StatusPaymentService statusPaymentService;

	@Autowired
	private NotificationService notificationService;

	@Override
	public List<Order> getAll() {
		return repo.findAll();
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
	public List<Order> getAllOrderByStatus(String status) {
		return repo.findByOrderStatusCode(status);
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
			String total = repo.getTotalPrice_Shipping_PerDay(then.getDayOfMonth() + "",
					then.getMonth().getValue() + "", then.getYear() + "",
					SecurityContextHolder.getContext().getAuthentication().getName(), "GH-TC");
			if (total != null) {
				day.setTotalPrice(Double.parseDouble(total));
				day.setPriceFormat(DecimalFormatUtil.FormatDecical(total) + "đ");
				System.out.println(day.getTotalPrice());
			} else {
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
	public OrderDto getByOrderCode(String orderCode, String status) {

		DecimalFormat formatter = new DecimalFormat("###,###,###");

		Order order = repo.findByOrderCodeAndOrderStatusCode(orderCode, status);
		if (order == null) {
			throw new IllegalArgumentException("Không tìm thấy sản phẩm");

		}
		OrderDto orderDto = mapper.map(order, OrderDto.class);
		OrderDetail orderDetail = orderDetailRepo.findByOrder(order);

		orderDto.setAddress(orderDetail.getAddress());
		orderDto.setProvince(orderDetail.getProvince());
		orderDto.setDistrict(orderDetail.getDistrict());
		orderDto.setWard(orderDetail.getWard());
		orderDto.setTel(orderDetail.getTel());

		orderDto.setCode(order.getOrderStatus().getCode());
		orderDto.setStatus(order.getOrderStatus().getStatus());

		List<OrderItem> orderItems = orderItemRepo.findByOrderId(order.getId());
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
		orderDto.setTotalPrice(formatter.format(repo.getTotalOrder(order.getId())));

		return orderDto;
	}

	@Override
	public DataOrderResquest getListOrder(String status) {

		DecimalFormat formatter = new DecimalFormat("###,###,###");

		List<Order> orders = repo.findByModifiedByAndOrderStatusCode(
				SecurityContextHolder.getContext().getAuthentication().getName(), status);
		if (orders == null) {
			throw new IllegalArgumentException("Không tìm thấy sản phẩm");

		}
		List<OrderDto> orderDtos = new ArrayList<>();

		orders.forEach(order -> {
			OrderDto orderDto = mapper.map(order, OrderDto.class);
			OrderDetail orderDetail = orderDetailRepo.findByOrder(order);

			orderDto.setAddress(orderDetail.getAddress());
			orderDto.setProvince(orderDetail.getProvince());
			orderDto.setDistrict(orderDetail.getDistrict());
			orderDto.setWard(orderDetail.getWard());
			orderDto.setTel(orderDetail.getTel());

			orderDto.setCode(order.getOrderStatus().getCode());
			orderDto.setStatus(order.getOrderStatus().getStatus());

			List<OrderItem> orderItems = orderItemRepo.findByOrderId(order.getId());
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
			orderDto.setTotalPrice(formatter.format(repo.getTotalOrder(order.getId())));
			orderDtos.add(orderDto);
		});
		DataOrderResquest data = new DataOrderResquest();
		data.setData(orderDtos);

		return data;
	}

	@Override
	public DataOrderResquest getListOrderByUser() {

		DecimalFormat formatter = new DecimalFormat("###,###,###");

		List<Order> orders = repo.findByModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		if (orders == null) {
			throw new IllegalArgumentException("Không tìm thấy sản phẩm");

		}
		List<OrderDto> orderDtos = new ArrayList<>();

		orders.forEach(order -> {
			OrderDto orderDto = mapper.map(order, OrderDto.class);
			OrderDetail orderDetail = orderDetailRepo.findByOrder(order);

			orderDto.setAddress(orderDetail.getAddress());
			orderDto.setProvince(orderDetail.getProvince());
			orderDto.setDistrict(orderDetail.getDistrict());
			orderDto.setWard(orderDetail.getWard());
			orderDto.setTel(orderDetail.getTel());

			orderDto.setCode(order.getOrderStatus().getCode());
			orderDto.setStatus(order.getOrderStatus().getStatus());

			List<OrderItem> orderItems = orderItemRepo.findByOrderId(order.getId());
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
			orderDto.setTotalPrice(formatter.format(repo.getTotalOrder(order.getId())));
			orderDtos.add(orderDto);
		});
		DataOrderResquest data = new DataOrderResquest();
		data.setData(orderDtos);

		return data;
	}

	@Override
	public String getToTalPricePerMonth() {
		YearMonth currentTimes = YearMonth.now();
		return repo.getToTalPricePerMonth(currentTimes.getMonthValue() + "", currentTimes.getYear() + "",
				SecurityContextHolder.getContext().getAuthentication().getName());

	}

	@Override
	public String getCountOrderPerMonth() {
		YearMonth currentTimes = YearMonth.now();
		return repo.getCountOrder(currentTimes.getMonthValue() + "", currentTimes.getYear() + "",
				SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@Override
	@Transactional
	public void pickupOrder(String orderCode, String code, String note) {
		try {
			Order order = repo.findByOrderCode(orderCode);
			if (order == null) {
				throw new IllegalArgumentException("Không tìm thấy sản phẩm");

			}
			OrderStatus status = orderStatusRepo.findByCode(code);
			if (status == null) {
				throw new IllegalArgumentException("Cập nhập thất bại");

			}
			order.setShipping_note(note);
			order.setOrderStatus(status);
			order.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			repo.saveAndFlush(order);
		
//          Gửi thông báo cho khách hàng
			Notification notify = new Notification();
				notify.setTitle("Đơn hàng " + order.getOrderCode() + status.getStatus().toLowerCase());
				notify.setUser(order.getUser());
				notify.setOrderCode(order.getOrderCode());
				notify.setIsWatched(Boolean.FALSE);
			notificationService.sendNotify(notify);
		} catch (Exception e) {
			throw new IllegalArgumentException("Cập nhập đơn hàng thấy bại");
		}

	}

	@Override
	public OrderDto findByCode(String orderCode) {
		DecimalFormat formatter = new DecimalFormat("###,###,###");

		Order order = repo.findByOrderCode(orderCode);
		if (order == null) {
			throw new IllegalArgumentException("Không tìm thấy sản phẩm");

		}
		OrderDto orderDto = mapper.map(order, OrderDto.class);
		OrderDetail orderDetail = orderDetailRepo.findByOrder(order);

		orderDto.setAddress(orderDetail.getAddress());
		orderDto.setProvince(orderDetail.getProvince());
		orderDto.setDistrict(orderDetail.getDistrict());
		orderDto.setWard(orderDetail.getWard());
		orderDto.setTel(orderDetail.getTel());

		orderDto.setCode(order.getOrderStatus().getCode());
		orderDto.setStatus(order.getOrderStatus().getStatus());

		List<OrderItem> orderItems = orderItemRepo.findByOrderId(order.getId());
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
		orderDto.setTotalPrice(formatter.format(repo.getTotalOrder(order.getId())));
		return orderDto;
	}

	@Override
	public Order changeOrderStatusCode(String orderCode) {
		Order order = repo.findByOrderCode(orderCode);
		if (order == null) {
			throw new IllegalArgumentException("Không tìm thấy đơn hàng");
		}
		if (order.getOrderStatus().getCode().equals("C-XN")) {
			order.setQrCode(QrCodeUtil.QrCode(order.getOrderCode(), order.getOrderCode()));
			order.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			order.setOrderStatus(orderStatusRepo.findByCode("C-LH"));

//            Gửi thông báo cho khách hàng
			Notification notify = new Notification();
			notify.setTitle("Đơn hàng " + order.getOrderCode() + " đã được xác nhận");
			notify.setUser(order.getUser());
			notify.setOrderCode(order.getOrderCode());
			notify.setIsWatched(Boolean.FALSE);
			notificationService.sendNotify(notify);

			return repo.saveAndFlush(order);

		} else if (order.getOrderStatus().getCode().equals("C-HUY")) {
			order.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			order.setOrderStatus(orderStatusRepo.findByCode("HUY"));
			return repo.saveAndFlush(order);
		}
		throw new IllegalArgumentException("Đơn hàng code_status = 'C-XN' hoặc 'C-HUY'!!");
	}

	@Override
	@Transactional
	public Order addOrder(Order orderResponse) {
		List<Cart> cartItem = cartService.doGetAllCartOrder();
		if (cartItem.isEmpty()) {
			throw new IllegalArgumentException("Giỏ hàng trống");
		}

		String orderCode = "";
		while (true) {
			String orderRamdom = OrderNumberUtil.get();
			if (this.isUniqueOrderNumber(orderRamdom)) {
				orderCode = orderRamdom;
				break;
			}

		}
		Users user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
		Order order = mapper.map(orderResponse, Order.class);
		order.setOrderCode(orderCode);
		order.setUser(user);
		order.setFullName(user.getFullname());
		order.setOrderStatus(orderStatusRepo.findByCode("C-XN"));
		order.setStatusPayment(statusPaymentService.findByCode("C-TT"));
		order.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		for (Cart cart : cartItem) {
			order.setUnitPrice(order.getUnitPrice() + (cart.getProduct().getPrice() * cart.getQuantity()));
			order.setOrderQuantity(order.getOrderQuantity() + cart.getQuantity());
		}
		Order orderNew = repo.saveAndFlush(order);

		cartRepository.deleteAll(cartItem);
		List<OrderItem> orderItems = MapperUtil.mapAll(cartItem, OrderItem.class);
		orderItems.forEach((item) -> {
			Product product = item.getProduct();
			if (product.getQuantity() <= 0) {
				throw new IllegalArgumentException("Sản phẩm tạm hết hàng");
			}
			product.setQuantity(product.getQuantity() - item.getQuantity());
			item.setId(null);
			item.setPrice(item.getProduct().getPrice());
			item.setOrder(orderNew);
			productRepo.save(product);
			orderItemRepo.saveAndFlush(item);
		});
		
		UserAddress address = addressService.findByUsernameAndChoose(Boolean.TRUE);
		orderNew.setFullName(address.getFullname());
		orderNew.setEmail(address.getEmail());
		repo.saveAndFlush(order);
		
		OrderDetail orderDetail = mapper.map(address, OrderDetail.class);
		orderDetail.setId(null);
		orderDetail.setOrder(orderNew);
		orderDetailRepo.saveAndFlush(orderDetail);
		Notification notify = new Notification();
			notify.setTitle("Đơn hàng của bạn đang chờ xác nhận. Mã đơn hàng của bạn: "+order.getOrderCode());
			notify.setUser(order.getUser());
			notify.setOrderCode(order.getOrderCode());
			notify.setIsWatched(Boolean.FALSE);
		notificationService.sendNotify(notify);
		return order;
	}

	@Override
	public Boolean isUniqueOrderNumber(String orderNumber) {
		return Objects.isNull(repo.findByOrderCode(orderNumber));
	}

	@Override
	public List<OrderStatus> getAllOrderStatus() {
		return orderStatusRepo.findAll();
	}

	@Override
	public List<PaymentMethods> getAllPayment() {
		return paymentService.getAll();
	}

	@Override
	public List<ShippingMethods> getAllShippingUnit() {
		return shippingService.getAll();
	}

	@Override
	public void cancelOrder(String orderCode) {
		Order order = repo.findByOrderCode(orderCode);
		List<OrderItem> productItems = orderItemRepo.findByOrderId(order.getId());
		if(order.getStatusPayment().getCode().equals("C-TT")) {
			productItems.forEach((item) -> {
				Product product = productService.findById(item.getId());
				if(product == null) {
					throw new IllegalArgumentException("Sản phẩm này không tồn tại");
				}
				product.setQuantity(product.getQuantity() + item.getQuantity());
				productRepo.saveAndFlush(product);
			});

			OrderStatus status = orderStatusRepo.findByCode("HUY");
			order.setOrderStatus(status);

			/*StatusPayment statusPayment = statusPaymentService.findByCode("D-HT");
			order.setStatusPayment(statusPayment);*/

			repo.saveAndFlush(order);

			if(!status.getCode().equals("LH-TB") || !status.getCode().equals("C-HUY" )){
//	          Gửi thông báo cho khách hàng
				Notification notify = new Notification();
				notify.setTitle("Đơn hàng " + order.getOrderCode() +" "+ status.getStatus().toLowerCase());
				notify.setUser(order.getUser());
				notify.setOrderCode(order.getOrderCode());
				notify.setIsWatched(Boolean.FALSE);
				notificationService.sendNotify(notify);
			}


		} else if (order.getStatusPayment().getCode().equals("D-TT")) {
			productItems.forEach((item) -> {
				Product product = productService.findById(item.getId());
				if(product == null) {
					throw new IllegalArgumentException("Sản phẩm này không tồn tại");
				}
				product.setQuantity(product.getQuantity() + item.getQuantity());
				productRepo.saveAndFlush(product);
				
				OrderStatus status = orderStatusRepo.findByCode("HUY");
				order.setOrderStatus(status);
				StatusPayment statusPayment = statusPaymentService.findByCode("C-HT");
				order.setStatusPayment(statusPayment);
				repo.saveAndFlush(order);
			});
		}
	}

	@Override
	public void refundMoney(String orderCode) {
		Order order = repo.findByOrderCode(orderCode);
		if(order.getStatusPayment().getCode().equals("C-HT")) {
			StatusPayment statusPayment = statusPaymentService.findByCode("D-HT");
			order.setStatusPayment(statusPayment);
			repo.saveAndFlush(order);
		}
	}

	@Override
	public Order finByOrderCodeAndUsername(String orderCode) {
		return repo.findByOrderCodeAndUserUsername(orderCode, SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@Override
	public List<Order> findByStatusCode(String statusCode) {
		return repo.findByOrderStatusCodeAndUserUsernameOrderByCreatedAtDesc(statusCode,SecurityContextHolder.getContext().getAuthentication().getName() );
	}

	@Override
	public List<Order> getAllByUser() {
		return repo.findByUserUsernameOrderByCreatedAtDesc(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@Override
	@Transactional
	public void cancelOrderByUserAndOrderCode(String orderCode,String statusOrder) {
		Order order = repo.findByOrderCodeAndUserUsername(orderCode, SecurityContextHolder.getContext().getAuthentication().getName());
		if(order == null) {
			throw new IllegalArgumentException("Không tìm thấy đơn hàng");
		}
		if(statusOrder.equals("C-HUY")) {
			order.setOrderStatus(orderStatusRepo.findByCode("C-HUY"));
			repo.saveAndFlush(order);
			
			Notification notify = new Notification();
			notify.setTitle("Trạng thái đơn hàng "+ order.getOrderCode()+": Chờ huỷ");
			notify.setUser(order.getUser());
			notify.setOrderCode(order.getOrderCode());
			notify.setIsWatched(Boolean.FALSE);
			notificationService.sendNotify(notify);
		}else if(statusOrder.equals("CANCEL-C-HUY")) {
			order.setOrderStatus(orderStatusRepo.findByCode("C-XN"));
				Notification notify = new Notification();
				notify.setTitle("Trạng thái đơn hàng "+ order.getOrderCode()+": Chờ xác nhận");
				notify.setUser(order.getUser());
				notify.setOrderCode(order.getOrderCode());
				notify.setIsWatched(Boolean.FALSE);
				notificationService.sendNotify(notify);
			repo.saveAndFlush(order);
			
		}
	}

	@Override
	public Long totalOrders(String orderStatusCode) {
		if (orderStatusCode != null && !orderStatusCode.isBlank()) {
			return repo.countByOrderStatusCode(orderStatusCode);
		} else {
			return repo.count();
		}
	}
	@Override
	public List<Object[]> getTotalByCategory(String month, String year) {
		return repo.getTotalByCategory(month, year);
	}
	@Override
	public String getTotalPricePerYear(String month, String year) {
		return repo.getTotalPricePerYear(month, year);
	}
	@Override
	public Double totalRevenue() {
		return orderItemRepo.getTotalRevenue("GH-TC");
	}

}
