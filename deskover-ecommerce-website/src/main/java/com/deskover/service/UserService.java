package com.deskover.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.deskover.dto.UserCreateDto;
import com.deskover.dto.ChangePasswordDto;
import com.deskover.entity.Users;

public interface UserService {
	 void changeActived(Long id);
	 Users findById(Long id);
	 Users findByUsername(String username);
	 DataTablesOutput<Users> getByActiveForDatatables(DataTablesInput input, Boolean isActive);
	 Users create(UserCreateDto userRequest);
	 void updatePassword(ChangePasswordDto userRequest);
}
