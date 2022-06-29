package com.deskover.service.impl;

import com.deskover.entity.AdminPassword;
import com.deskover.repository.AdminPasswordRepository;
import com.deskover.service.AdminPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class AdminPasswordServiceImpl implements AdminPasswordService{
	private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

	@Autowired 
	AdminPasswordRepository repo;


	@Override
	public AdminPassword getById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public AdminPassword create(AdminPassword adminPassword) {
		if(adminPassword == null){
			throw new IllegalArgumentException("Thêm mới password không thành công");
		}
		String hashPassword = bcrypt.encode(adminPassword.getPassword());
		adminPassword.setPassword(hashPassword);
		adminPassword.setModifiedAt(null);
		adminPassword.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		return repo.save(adminPassword);
	}
}
