package com.deskover.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.multipart.MultipartFile;

import com.deskover.model.entity.database.Users;
import com.deskover.model.entity.dto.ChangePasswordDto;
import com.deskover.model.entity.dto.UserCreateDto;

public interface UserService {
	 Users findById(Long id);
	 Users findByUsername(String username);
	 DataTablesOutput<Users> getByActiveForDatatables(DataTablesInput input, Boolean isActive);
	 Users create(UserCreateDto userRequest);
	 Users uploadFile(MultipartFile file);
	 Users update(Users user);
	 void changeActived(Long id);
	 void updatePassword(ChangePasswordDto userRequest);
}
