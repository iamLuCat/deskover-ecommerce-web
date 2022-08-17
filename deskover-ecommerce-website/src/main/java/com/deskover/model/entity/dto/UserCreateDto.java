package com.deskover.model.entity.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
	@NotBlank(message = "Không bỏ trống username")
	private String username;
	@NotBlank(message = "Không bỏ trống password")
	private String password;
	@NotBlank(message = "Không bỏ trống confirmPassword")
	private String confirmPassword;
	@NotBlank(message = "Không bỏ trống fullname")
	private String fullname;
}
