package com.deskover.service;

import com.deskover.dto.ghtk.resquest.OrderShippingResquest;
import com.deskover.entity.Order;

public interface GHTKService {
	
	OrderShippingResquest ShipmentOrder(Order order,String header);//Đăng đơn hàng
	
	void loadOrderStatus(String header) throws Exception;

}
