package com.deskover.service;

import java.util.List;

import javax.validation.Valid;

import com.deskover.entity.UserAddress;

public interface UserAddressService {
	List<UserAddress> findByUsername();
	void changeActive(Long id);
	void changeChoose(Long id);
	UserAddress findByUsernameAndChoose(Boolean choose);
	UserAddress doPostAddAddress(UserAddress userAddress);
	UserAddress doPutAddAddress(@Valid UserAddress userAddress);
}
