package com.deskover.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdatePassDto {
	private Long id;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
}
