package com.deskover.service;

import com.deskover.entity.StatusPayment;

public interface StatusPaymentService {
	StatusPayment findByCode(String code);
}
