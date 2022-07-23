package com.deskover.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.entity.UserAddress;
import com.deskover.repository.UserAddressRepository;
import com.deskover.service.UserAddressService;

@Service
public class UserAddressServiceImpl implements UserAddressService {
	@Autowired
	private UserAddressRepository userAddressRepository;

	@Override
	public List<UserAddress> findByUsername(String username) {
		List<UserAddress> contact = userAddressRepository.findByUserUsername(username);
		if(contact == null) {
			throw new IllegalArgumentException("Không có địa chỉ");
		}
		return contact;
	}

	@Override
	@Transactional
	public void changeActive(Long id, String username) {
		
		List<UserAddress> userAddresses = userAddressRepository.findByUserUsername(username);
		UserAddress userAddress = userAddressRepository.getById(id);
		if(userAddress == null) {
			throw new IllegalArgumentException("Không tìm thấy địa chỉ");
		}
		userAddresses.forEach((address)->{
			if(userAddress.getId() == address.getId()) {
				userAddress.setActived(Boolean.TRUE);
				userAddressRepository.saveAndFlush(userAddress);
			}else if (userAddress.getId() != address.getId()) {
				address.setActived(Boolean.FALSE);
				userAddressRepository.saveAndFlush(address);
			}
		}
		);	
	}

	@Override
	@Transactional
	public void changeChoose(Long id, String username) {
		List<UserAddress> userAddresses = userAddressRepository.findByUserUsername(username);
		UserAddress userAddress = userAddressRepository.getById(id);
		if(userAddress == null) {
			throw new IllegalArgumentException("Không tìm thấy địa chỉ");
		}
		userAddresses.forEach((address)->{
			if(userAddress.getId() == address.getId()) {
				userAddress.setChoose(Boolean.TRUE);
				userAddressRepository.saveAndFlush(userAddress);
			}else if (userAddress.getId() != address.getId()) {
				address.setChoose(Boolean.FALSE);
				userAddressRepository.saveAndFlush(address);
			}
		}
		);
		
	}

	@Override
	public UserAddress findByUsernameAndChoose(String username,Boolean choose) {
		return userAddressRepository.findByUserUsernameAndChoose(username, choose);
	}

}
