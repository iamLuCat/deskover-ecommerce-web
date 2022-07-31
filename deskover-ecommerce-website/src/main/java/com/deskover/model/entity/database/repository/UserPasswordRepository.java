package com.deskover.model.entity.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.UserPassword;
import com.deskover.model.entity.database.Users;

public interface UserPasswordRepository extends JpaRepository<UserPassword, Long> {
	UserPassword findByUserId(Long id);
	UserPassword findByUserUsername(String username);
}