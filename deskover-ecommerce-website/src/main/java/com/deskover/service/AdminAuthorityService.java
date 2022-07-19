package com.deskover.service;

import com.deskover.dto.AdminAuthorityDto;
import com.deskover.entity.AdminAuthority;

import java.util.Set;

public interface AdminAuthorityService {
	Set<AdminAuthorityDto> getByAdminId(Long id);
	AdminAuthority create(AdminAuthority adminAuthority);
}
