package com.deskover.configuration.userdetail;

import com.deskover.entity.UserPassword;
import com.deskover.entity.Users;
import com.deskover.service.UserPasswordService;
import com.deskover.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Configurable
public class UsersDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserPasswordService passwordService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Users user = userService.findUser(username.trim());
            UserPassword userPassword = passwordService.getPasswordByUsername(user.getUsername());
            userService.updateTimestamp(user);
            return new User(
            		user.getUsername(),
            		userPassword.getPassword(),
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
