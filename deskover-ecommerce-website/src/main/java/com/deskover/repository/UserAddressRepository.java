package com.deskover.repository;

import com.deskover.entity.UserAddress;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
	List<UserAddress> findByUserUsername(String username);
	
}