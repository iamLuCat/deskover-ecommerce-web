package com.deskover.configuration.security.jwt.entity;

import com.deskover.dto.AdminAuthorityDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {
	private static final long serialVersionUID = -8091879091924046844L;
	private String token;
	private String fullname;
	private Set<AdminAuthorityDto> authorities;
}
