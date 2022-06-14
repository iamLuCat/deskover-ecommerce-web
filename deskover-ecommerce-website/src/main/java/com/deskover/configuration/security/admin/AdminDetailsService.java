package com.deskover.configuration.security.admin;

import com.deskover.entity.Administrator;
import com.deskover.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsService implements UserDetailsService {
    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrator admin = adminService.getByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("No admin found with username: " + username);
        }
        return new AdminDetails(admin);
    }
}
