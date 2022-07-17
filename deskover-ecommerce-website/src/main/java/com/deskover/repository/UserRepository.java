package com.deskover.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findAll();
	Page<User> findByActived(Boolean actived, Pageable Page);
}