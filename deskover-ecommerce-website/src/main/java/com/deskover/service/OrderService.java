package com.deskover.service;

import com.deskover.model.entity.database.Order;
import com.deskover.model.entity.database.OrderStatus;
import com.deskover.model.entity.database.PaymentMethods;
import com.deskover.model.entity.database.ShippingMethods;
import com.deskover.model.entity.dto.application.DataOrderResquest;
import com.deskover.model.entity.dto.application.DataTotaPrice7DaysAgo;
import com.deskover.model.entity.dto.application.OrderDto;

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

	void cancelOrderByUserAndOrderCode(String orderCode);


	

}
