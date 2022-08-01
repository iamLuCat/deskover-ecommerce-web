package com.deskover.service;

import com.deskover.model.entity.database.StatusPayment;

public interface StatusPaymentService {
	StatusPayment findByCode(String code);
}
