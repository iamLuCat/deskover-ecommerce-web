package com.deskover.service;

import java.util.ArrayList;
import java.util.List;

import com.deskover.model.entity.database.UserAddress;
import com.deskover.model.entity.dto.ProductDto;

public interface CheckoutService {
	void saveOrder(UserAddress entity, String total, ArrayList<ProductDto> items);
	void saveOrderPay(UserAddress entity, String total, ArrayList<ProductDto> items);
}
