package com.deskover.model.entity.dto.ecommerce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO {
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
}
