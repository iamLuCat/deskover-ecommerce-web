package com.deskover.model.entity.database.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.Users;
import com.deskover.model.entity.dto.UserCreateDto;

public interface UserRepository extends JpaRepository<Users, Long> {
	List<Users> findAll();
	Page<Users> findByActived(Boolean actived, Pageable Page);
	Users findByUsername(String username);
	Boolean existsByUsername(String username);
}