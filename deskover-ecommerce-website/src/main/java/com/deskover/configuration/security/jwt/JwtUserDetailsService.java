package com.deskover.configuration.security.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.deskover.entity.Administrator;
import com.deskover.entity.UserPassword;
import com.deskover.entity.Users;
import com.deskover.service.AdminService;
import com.deskover.service.UserPasswordService;
import com.deskover.service.UserService;

@Service
@Configurable
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserPasswordService userPasswordService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Administrator admin = adminService.getByUsername(username);
            if(admin == null) {
            	Users user = userService.findByUsername(username);
            	UserPassword userPassword = userPasswordService.getPasswordByUsername(username);
            	 List<GrantedAuthority> grantList = new ArrayList<>();
            	 GrantedAuthority authority = new SimpleGrantedAuthority("CUSTOMER");
            	 grantList.add(authority);
            	return new User(
            			user.getUsername(),
            			userPassword.getPassword() ,
            			user.getActived(),
                        true,
                        true,
                        true,
                        (Collection<? extends GrantedAuthority>) grantList
                );
            }
            return new User(
                    admin.getUsername(),
                    admin.getPassword() ,
                    admin.getActived(),
                    true,
                    true,
                    true,
                    admin.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getRole().getRoleId()))
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

    }

}
