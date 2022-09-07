package com.deskover.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.entity.Users;
import com.deskover.entity.Verify;
import com.deskover.reponsitory.VerifyRepository;
import com.deskover.service.VerifyService;

@Service
public class VerifyServiceImpl implements VerifyService{
	@Autowired VerifyRepository verifyRepo;

	@Override
	public Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
	}
	
	@Override
	public void createVerifyToken(String token, Users user) {
		Verify verifyToken = new Verify();
		verifyToken.setExpiryDate(calculateExpiryDate(1200));
		verifyToken.setToken(token);
		verifyToken.setUser(user);
		verifyRepo.save(verifyToken);
	}
	
    //  Truyền String token vào để Get entity Token
	@Override
	public Verify getVerificationToken(String VerificationToken) {
		return verifyRepo.findByToken(VerificationToken);
	}

	@Override
	public Verify findByUser(Users user) {
		return verifyRepo.findByUser(user);
	}

	@Transactional
	@Override
	public Verify save(Verify verify) {
		return verifyRepo.save(verify);
	}
}
