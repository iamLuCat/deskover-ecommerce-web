package com.deskover.service;

import com.deskover.model.entity.database.UserPassword;
import com.deskover.model.entity.database.Users;
import com.deskover.model.entity.dto.ChangePasswordDto;

public interface UserPasswordService {
	UserPassword create(Users user,String password);
	void updatePassword(String username,ChangePasswordDto updatePasswordUser);
	UserPassword getPasswordByUsername(String username);
}
