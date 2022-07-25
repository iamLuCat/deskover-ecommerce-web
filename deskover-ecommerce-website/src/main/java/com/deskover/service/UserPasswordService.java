package com.deskover.service;

import com.deskover.dto.ChangePasswordDto;
import com.deskover.entity.User;
import com.deskover.entity.UserPassword;

public interface UserPasswordService {
	UserPassword create(User user,String password);
	void updatePassword(String username,ChangePasswordDto updatePasswordUser);
}
