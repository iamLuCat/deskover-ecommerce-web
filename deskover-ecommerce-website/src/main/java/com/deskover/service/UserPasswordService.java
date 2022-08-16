package com.deskover.service;

import org.springframework.http.ResponseEntity;

import com.deskover.model.entity.database.UserPassword;
import com.deskover.model.entity.database.Users;
import com.deskover.model.entity.dto.ChangePasswordDto;
import com.deskover.model.entity.dto.ecommerce.PasswordDTO;

public interface UserPasswordService {
	UserPassword create(Users user,String password);
	void updatePassword(String username,ChangePasswordDto updatePasswordUser);
	UserPassword getPasswordByUsername(String username);
	ResponseEntity<?> updatePassword(String username,PasswordDTO form);
}
