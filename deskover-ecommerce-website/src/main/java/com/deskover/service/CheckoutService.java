package com.deskover.service;

import com.deskover.model.entity.database.UserAddress;

public interface CheckoutService {
	void saveOrder(UserAddress entity, String total);
}
