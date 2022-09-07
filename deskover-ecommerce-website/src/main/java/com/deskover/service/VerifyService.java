package com.deskover.service;

import java.sql.Date;

import com.deskover.entity.Users;
import com.deskover.entity.Verify;

public interface VerifyService {
	void createVerifyToken(String token, Users user);
	Verify getVerificationToken(String VerificationToken);
	Date calculateExpiryDate(int expiryTimeInMinutes);
	Verify findByUser(Users user);
	Verify save(Verify verify);
}
