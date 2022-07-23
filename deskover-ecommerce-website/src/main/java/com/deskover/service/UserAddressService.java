package com.deskover.service;

import java.util.List;

import com.deskover.entity.UserAddress;

public interface UserAddressService {
	List<UserAddress> findByUsername(String username);
	
	void changeActive(Long id,String username);
}
