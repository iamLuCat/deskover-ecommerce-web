package com.deskover.service;

import java.util.Set;

import com.deskover.model.entity.database.AdminAuthority;

public interface AdminAuthorityService {
	Set<AdminAuthority> getByAdminId(Long id);
	AdminAuthority create(AdminAuthority adminAuthority);
	AdminAuthority findByAdminIdAndRoleId(Long adminId, Long roleId);
	void changeRole(Long adminId, Long roleId);
}
