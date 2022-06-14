package com.deskover.service;

import com.deskover.entity.Administrator;

public interface AdminService {

    Administrator getByUsername(String username);
}
