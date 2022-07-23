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
	private UserAddressRepository contactRepository;

	@Override
	public List<UserAddress> findByUsername(String username) {
		List<UserAddress> contact = contactRepository.findByUserUsername(username);
		if(contact == null) {
			throw new IllegalArgumentException("Không có địa chỉ");
		}
		return contact;
	}

	@Override
	@Transactional
	public void changeActive(Long id, String username) {
		
		List<UserAddress> userAddresses = contactRepository.findByUserUsername(username);
		UserAddress userAddress = contactRepository.getById(id);
		if(userAddress == null) {
			throw new IllegalArgumentException("Không tìm thấy địa chỉ");
		}
		userAddresses.forEach((address)->{
			if(userAddress.getId() == address.getId()) {
				userAddress.setActived(Boolean.TRUE);
				contactRepository.saveAndFlush(userAddress);
			}else if (userAddress.getId() != address.getId()) {
				address.setActived(Boolean.FALSE);
				contactRepository.saveAndFlush(address);
			}
		}
		);	
	}

}
