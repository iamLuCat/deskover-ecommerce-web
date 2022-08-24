package com.deskover.model.entity.dto.ecommerce;

import com.deskover.model.entity.database.Users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
	
	public AccountDTO(Users user) {
		this.fullname = user.getFullname();
		this.avatar = user.getAvatar();
		this.email = user.getEmail();
		this.phone = user.getPhone();
	}
	
	private String fullname;
	private String avatar;
	private String email;
	private String phone;
}	
