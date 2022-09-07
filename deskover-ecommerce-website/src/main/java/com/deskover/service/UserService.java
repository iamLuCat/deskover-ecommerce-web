package com.deskover.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

import com.deskover.dto.ChangePasswordDto;
import com.deskover.dto.UserCreateDto;
import com.deskover.dto.ecommerce.AccountDTO;
import com.deskover.dto.ecommerce.AccountFormDTO;
import com.deskover.entity.Users;

public interface UserService {
	 Users findById(Long id);
	 Users findByUsername(String username);
	 Users findUser(String username);
	 DataTablesOutput<Users> getByActiveForDatatables(DataTablesInput input, Boolean isActive);
	 Users create(UserCreateDto userRequest);
	 Users create1(UserCreateDto userRequest);
	 Users uploadFile(MultipartFile file);
	 Users update(Users user);
	 void changeActived(Long id);
	 void updatePassword(ChangePasswordDto userRequest);
	 void updateTimestamp(Users user);
	 void updateProfile(AccountFormDTO form, String username);
	 void updateAvarta(MultipartFile file, String username);
	 Long totalCustomers();
	 
	 void loginFromOAuth2(OAuth2AuthenticationToken oauth2);
	 AccountDTO getAccountInfo(String username);

}
