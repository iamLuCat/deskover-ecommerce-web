package com.deskover.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.deskover.entity.User;

public interface UserService {
	void changeActived(Long id);
	Page<User> findByActived(Boolean actived, Pageable Page);
}
