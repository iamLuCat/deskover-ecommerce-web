package com.deskover.service;

import org.springframework.http.ResponseEntity;

import com.deskover.dto.ChangePasswordDto;
import com.deskover.dto.ecommerce.PasswordDTO;
import com.deskover.entity.UserPassword;
import com.deskover.entity.Users;

public interface UserPasswordService {
	UserPassword create(Users user,String password);
	void updatePassword(String username,ChangePasswordDto updatePasswordUser);
	UserPassword getPasswordByUsername(String username);
	ResponseEntity<?> resetPassword(String phone,String passwordNew, String confirmPassword);
	ResponseEntity<?> updatePassword(String username,PasswordDTO form);
}
