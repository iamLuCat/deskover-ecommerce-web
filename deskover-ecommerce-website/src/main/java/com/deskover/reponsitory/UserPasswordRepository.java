package com.deskover.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.UserPassword;
import com.deskover.entity.Users;

public interface UserPasswordRepository extends JpaRepository<UserPassword, Long> {
	UserPassword findByUserId(Long id);
	UserPassword findByUserUsername(String username);
}