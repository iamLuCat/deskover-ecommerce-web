package com.deskover.service;

import com.deskover.dto.application.DataOrderResquest;
import com.deskover.dto.application.DataTotaPrice7DaysAgo;
import com.deskover.dto.application.OrderDto;
import com.deskover.entity.Order;
import com.deskover.entity.OrderStatus;
import com.deskover.entity.PaymentMethods;
import com.deskover.entity.ShippingMethods;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

public interface OrderService {
	DataTablesOutput<Order> getAllForDatatables(DataTablesInput input, String statusCode);
	
	OrderDto getByOrderCode(String orderCode, String status);
	OrderDto findByCode(String orderCode);
	
	DataOrderResquest getListOrder(String status);
	DataOrderResquest getListOrderByUser();
	DataTotaPrice7DaysAgo doGetTotalPrice7DaysAgo();
	
	String getToTalPricePerMonth();
	String getCountOrderPerMonth();
	
	Order finByOrderCodeAndUsername(String orderCode);
	Order changeOrderStatusCode(String orderCode);
	Order addOrder(Order orderResponse);
	
	List<OrderStatus> getAllOrderStatus();
	List<PaymentMethods> getAllPayment();
	List<ShippingMethods> getAllShippingUnit();
	List<Order> findByStatusCode(String statusCode);
	List<Order> getAllByUser();
	List<Order> getAllOrderByStatus(String statusCode);
	List<Order> getAll();
	
	void cancelOrder(String orderCode);
	void refundMoney(String orderCode);
	void pickupOrder(String orderCode,String code, String note);
	
	Boolean isUniqueOrderNumber(String orderNumber);

	void cancelOrderByUserAndOrderCode(String orderCode,String statusOrder);
	void cancelOrderByUserAndOrderCode1(String orderCode,String statusOrder);

    Long totalOrders(String orderStatusCode);
    List<Object[]> getTotalByCategory(String month, String year);
	String getTotalPricePerYear(String month, String year);
	Double totalRevenue();
	
	List<Order> getByPhone(String phone);

    Long countByStatus(String orderStatusCode);
}
