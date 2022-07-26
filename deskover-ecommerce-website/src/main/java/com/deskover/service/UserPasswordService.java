package com.deskover.service;

import com.deskover.dto.ChangePasswordDto;
import com.deskover.entity.Users;
import com.deskover.entity.UserPassword;

public interface UserPasswordService {
	UserPassword create(Users user,String password);
	void updatePassword(String username,ChangePasswordDto updatePasswordUser);
	UserPassword getPasswordByUsername(String username);
}
