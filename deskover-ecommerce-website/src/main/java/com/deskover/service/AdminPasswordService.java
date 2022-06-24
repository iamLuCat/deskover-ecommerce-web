package com.deskover.service;

import com.deskover.entity.AdminPassword;

public interface AdminPasswordService {
	AdminPassword getById(Long id);
	AdminPassword create(AdminPassword adminPassword);
}
