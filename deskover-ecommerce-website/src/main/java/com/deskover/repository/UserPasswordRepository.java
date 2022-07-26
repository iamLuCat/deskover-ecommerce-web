package com.deskover.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.Users;
import com.deskover.entity.UserPassword;

public interface UserPasswordRepository extends JpaRepository<UserPassword, Long> {
	UserPassword findByUserId(Long id);
	UserPassword findByUserUsername(String username);
}