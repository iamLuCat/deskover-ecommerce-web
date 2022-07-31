package com.deskover.service;

import java.util.List;

import com.deskover.model.entity.database.ShippingMethods;

public interface ShippingService {
	List<ShippingMethods> getAll();
	ShippingMethods findById(Long id);

}
