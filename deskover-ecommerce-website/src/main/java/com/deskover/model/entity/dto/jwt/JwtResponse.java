package com.deskover.model.entity.dto.jwt;

import com.deskover.model.entity.database.AdminAuthority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {
	private static final long serialVersionUID = -8091879091924046844L;
	private String token;
	private String fullname;
	private String avatar;
	/*private Set<AdminAuthority> authorities;*/
	private AdminAuthority authority;
}
