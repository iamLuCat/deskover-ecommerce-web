package com.deskover.service;

import java.util.ArrayList;
import java.util.List;

import com.deskover.dto.ProductDto;
import com.deskover.entity.UserAddress;

public interface CheckoutService {
	void saveOrder(UserAddress entity, String total, ArrayList<ProductDto> items);
	void saveOrderPay(UserAddress entity, String total, ArrayList<ProductDto> items);
}
