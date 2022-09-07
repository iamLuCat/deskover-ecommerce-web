package com.deskover.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
	List<UserAddress> findByUserUsernameOrderByActivedDesc(String username);
	UserAddress findByUserUsernameAndChoose(String username, Boolean choose);
	Boolean existsByTel(String tel);
	
}