package com.deskover.service;

import com.deskover.dto.ghtk.resquest.OrderShippingRequest;
import com.deskover.entity.Order;

public interface GHTKService {
	
	OrderShippingRequest shipmentOrder(Order order, String header);//Đăng đơn hàng
	
	void loadOrderStatus(String header) throws Exception;

}
