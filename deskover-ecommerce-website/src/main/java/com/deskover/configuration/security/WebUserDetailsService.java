package com.deskover.configuration.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.deskover.entity.Users;
import com.deskover.service.UserService;

@Service
@Configurable
public class WebUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Users user = userService.findByUsername(username);
            return new User(
            		user.getUsername(),
            		user.getUserPassword().getPassword(),
            		user.getActived(),
                    true,
                    true,
                    true,
                    List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
            );
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

    }

}
