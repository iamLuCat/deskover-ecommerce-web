package com.deskover.service;

import com.deskover.entity.Administrator;

public interface AdminService {
    Administrator getById(Long id);
    Administrator getByUsername(String username);
}
