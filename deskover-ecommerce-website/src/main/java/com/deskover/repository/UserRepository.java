package com.deskover.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.dto.UserCreateDto;
import com.deskover.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	List<Users> findAll();
	Page<Users> findByActived(Boolean actived, Pageable Page);
	Users findByUsername(String username);
	Boolean existsByUsername(String username);
}