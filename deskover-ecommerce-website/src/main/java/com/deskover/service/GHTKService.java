package com.deskover.service;

import com.deskover.dto.ghtk.entity.FeeGhtk;
import com.deskover.dto.ghtk.response.AddressResponseData;
import com.deskover.dto.ghtk.response.FeeResponseData;
import com.deskover.dto.ghtk.resquest.OrderShippingResquest;
import com.deskover.entity.Order;

public interface GHTKService {
	
	OrderShippingRequest shipmentOrder(Order order, String header);//Đăng đơn hàng
	
	void loadOrderStatus(String header) throws Exception;
	
	FeeResponseData doGetFee(FeeGhtk fee, String token) throws Exception;//Lấy Fee
	
	AddressResponseData doGetAddress(String Token) throws Exception;

}
