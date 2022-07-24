package com.deskover.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.entity.User;
import com.deskover.entity.UserAddress;
import com.deskover.repository.UserAddressRepository;
import com.deskover.service.UserAddressService;
import com.deskover.service.UserService;

@Service
public class UserAddressServiceImpl implements UserAddressService {
	@Autowired
	private UserAddressRepository userAddressRepository;
	
	@Autowired
	private UserService userService;

	@Override
	public List<UserAddress> findByUsername(String username) {
		List<UserAddress> listAddress = userAddressRepository.findByUserUsername(username);
		if(listAddress == null) {
			throw new IllegalArgumentException("Không có địa chỉ");
		}
//		listAddress.forEach((address)->{
//			if(address.getActived()) {
//				address.setChoose(Boolean.TRUE);
//				userAddressRepository.save(address);
//			}else if (!address.getActived()) {
//				address.setChoose(Boolean.FALSE);
//				userAddressRepository.save(address);
//			}
//		});
		return listAddress;
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

	@Override
	@Transactional
	public UserAddress doPostAddAddress(UserAddress userAddress, String username) {
		User user = userService.findByUsername(username);
		userAddress.setUser(user);
		userAddress.setActived(Boolean.FALSE);
		userAddress.setChoose(Boolean.FALSE);
		return userAddressRepository.saveAndFlush(userAddress);
	}

}
