package com.deskover.configuration.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deskover.entity.Administrator;
import com.deskover.entity.Users;
import com.deskover.service.AdminService;
import com.deskover.service.UsersService;


@Service
public class JwtUserDetailsService implements UserDetailsService {	
	@Autowired
    private AdminService adminService;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			return new User(
					username, 
					bcryptEncoder.encode("12345678"),
					new ArrayList<GrantedAuthority>()
					);
		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		
	}

}
