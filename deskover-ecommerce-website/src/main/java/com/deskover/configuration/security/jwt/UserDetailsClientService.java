//package com.deskover.configuration.security.jwt;
//
//import java.util.ArrayList;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.deskover.entity.UserPassword;
//import com.deskover.entity.Users;
//import com.deskover.service.UserPasswordService;
//import com.deskover.service.UserService;
//
//@Service
//@Configurable
//public class UserDetailsClientService implements UserDetailsService {
//	
//    @Autowired
//    private UserService userService;
//    
//    @Autowired
//    private UserPasswordService userPasswordService;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		try {
//	            	Users user = userService.findByUsername(username);
//	            	UserPassword userPassword = userPasswordService.getPasswordByUsername(username);
//	            	 ArrayList<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//	            	 GrantedAuthority authority = new SimpleGrantedAuthority("CUSTOMER");
//	            	 grantList.add(authority);
//	            	return new User(
//	            			user.getUsername(),
//	            			userPassword.getPassword() ,
//	            			user.getActived(),
//	                        true,
//	                        true,
//	                        true,
//	                        grantList
//	                );
//		} catch (Exception e) {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
//	}
//
//}
