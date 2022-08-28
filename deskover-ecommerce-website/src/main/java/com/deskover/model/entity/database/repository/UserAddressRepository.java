package com.deskover.model.entity.database.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
	List<UserAddress> findByUserUsernameOrderByActivedDesc(String username);
	UserAddress findByUserUsernameAndChoose(String username, Boolean choose);
	Boolean existsByTel(String tel);
	
}