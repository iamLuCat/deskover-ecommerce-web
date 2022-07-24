package com.deskover.service;

import java.util.List;

import com.deskover.entity.ShippingMethods;

public interface ShippingService {
	List<ShippingMethods> doGetAll();
	ShippingMethods findById(Long id);

}
