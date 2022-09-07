package com.deskover.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.dto.ChangePasswordDto;
import com.deskover.dto.MessageResponse;
import com.deskover.dto.ecommerce.PasswordDTO;
import com.deskover.entity.UserPassword;
import com.deskover.entity.Users;
import com.deskover.reponsitory.UserPasswordRepository;
import com.deskover.service.UserPasswordService;
import com.deskover.service.UserService;

@Service
public class UserPasswordServiceImpl implements UserPasswordService{
	private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	
	@Autowired
	UserPasswordRepository repo;
	
	@Autowired 
	UserService userService;
	
	@Override
	public UserPassword create(Users user, String password) {
		String hashPassword = bcrypt.encode(password);
		UserPassword userPass = new UserPassword();
		userPass.setUser(user);
		userPass.setPassword(hashPassword);
		userPass.setModifiedAt(new Timestamp(System.currentTimeMillis()));
		return repo.save(userPass);
	}
	
	@Override
	@Transactional
	public void updatePassword(String username, ChangePasswordDto updatePasswordUser) {
		Users userRequestUser = userService.findByUsername(username);
		if(userRequestUser == null) {
			throw new IllegalArgumentException("User này không tồn tại");
		}
		UserPassword userPassExists = repo.findByUserId(userRequestUser.getId());
		if(userPassExists == null) {
			throw new IllegalArgumentException("User này không tồn tại");
		}
		if(!bcrypt.matches(updatePasswordUser.getOldPassword(), userPassExists.getPassword())) {
			throw new IllegalArgumentException("Mật khẩu cũ không đúng");
		}else {
			if(updatePasswordUser.getNewPassword().equals(updatePasswordUser.getOldPassword())) {
				throw new IllegalArgumentException("Mật khẩu mới không được trùng với mật khẩu cũ");
			}else {
				if(!updatePasswordUser.getConfirmPassword().equals(updatePasswordUser.getNewPassword())) {
					throw new IllegalArgumentException("Mật khẩu xác nhận không đúng");
				}else {
					String hashPass = bcrypt.encode(updatePasswordUser.getConfirmPassword());
					userPassExists.setPassword(hashPass);
					repo.saveAndFlush(userPassExists);
				}
			}
		}
	}

	@Override
	public UserPassword getPasswordByUsername(String username) {
		UserPassword userPassword = repo.findByUserUsername(username);
		if(userPassword==null) {
			throw new IllegalArgumentException("Không tìm thấy user");
		}
		return userPassword;
	}

	@Override
	public ResponseEntity<?> updatePassword(String username, PasswordDTO form) {
		UserPassword password = repo.findByUserUsername(username);
		
		if(!bcrypt.matches(form.getOldPassword(), password.getPassword())) {
			return  ResponseEntity.badRequest().body(new MessageResponse("Mật khẩu cũ không đúng, hãy nhập lại!")) ;
		}
		if(!form.getConfirmPassword().equals(form.getNewPassword())) {
			return  ResponseEntity.badRequest().body(new MessageResponse("Mật khẩu mới không trùng, hãy nhập lại!")) ;
		}
		if(form.getNewPassword().equals(form.getOldPassword())) {
			return  ResponseEntity.badRequest().body(new MessageResponse("Mật khẩu mới không được trùng với mật khẩu cũ, hãy nhập lại!")) ;
		}
		String hashPass = bcrypt.encode(form.getConfirmPassword());
		password.setPassword(hashPass);
		repo.saveAndFlush(password);
		return  ResponseEntity.ok().body(new MessageResponse("Mật khẩu đã được thay đổi thành công")) ;
	}

	@Override
	public ResponseEntity<?> resetPassword(String phone, String passwordNew, String confirmPassword) {
		UserPassword password = repo.findByUserUsername(phone);
		if(!passwordNew.equals(confirmPassword)) {
			return  ResponseEntity.badRequest().body(new MessageResponse("Mật khẩu mới không trùng, hãy nhập lại!")) ;
		}
		String hashPass = bcrypt.encode(passwordNew);
		password.setPassword(hashPass);
		repo.saveAndFlush(password);
		return  ResponseEntity.ok().body(new MessageResponse("Mật khẩu đã được thay đổi thành công")) ;
	}

}
