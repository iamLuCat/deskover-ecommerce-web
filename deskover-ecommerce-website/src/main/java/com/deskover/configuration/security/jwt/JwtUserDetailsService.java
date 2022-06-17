package com.deskover.configuration.security.jwt;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deskover.entity.Administrator;
import com.deskover.service.AdminService;


@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Administrator admin = adminService.getByUsername(username);
            return new User(
                    admin.getUsername(),
                    admin.getAdminPasswords().getPassword(),
                    admin.getActived(),
                    true,
                    true,
                    true,
                    admin.getAdminAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getRole().getName()))
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

    }

}
