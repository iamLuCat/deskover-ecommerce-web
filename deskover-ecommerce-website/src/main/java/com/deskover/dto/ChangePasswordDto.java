package com.deskover.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
	@NotBlank(message = "Vui lòng nhập mật khẩu cũ")
	private String oldPassword;
	@NotBlank(message = "Vui lòng nhập mật khẩu mới")
	private String newPassword;
	@NotBlank(message = "Vui lòng nhập xác nhận mật khẩu mới")
	private String confirmPassword;
}
