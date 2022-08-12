package com.deskover.service;

import java.sql.Date;

import com.deskover.model.entity.database.Users;
import com.deskover.model.entity.database.Verify;

public interface VerifyService {
	void createVerifyToken(String token, Users user);
	Verify getVerificationToken(String VerificationToken);
	Date calculateExpiryDate(int expiryTimeInMinutes); 
}
