package com.deskover.service;

import com.deskover.model.entity.database.Order;
import com.deskover.model.entity.extend.ghtk.FeeGhtk;
import com.deskover.model.entity.extend.ghtk.response.AddressResponseData;
import com.deskover.model.entity.extend.ghtk.response.FeeResponseData;
import com.deskover.model.entity.extend.ghtk.resquest.OrderShippingRequest;

public interface GHTKService {
	
	OrderShippingRequest shipmentOrder(Order order, String header);//Đăng đơn hàng
	
	void loadOrderStatus(String header) throws Exception;
	
	FeeResponseData doGetFee(FeeGhtk fee, String token) throws Exception;//Lấy Fee
	
	AddressResponseData doGetAddress(String Token) throws Exception;

}
