package com.deskover.service;

import com.deskover.model.entity.database.AdminRole;

public interface AdminRoleService {
    AdminRole getById(Long id);
    AdminRole getByRoleId(String roleId);
}
