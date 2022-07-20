package com.deskover.service.impl;

import com.deskover.entity.AdminRole;
import com.deskover.repository.AdminRoleRepository;
import com.deskover.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminRoleServiceImpl implements AdminRoleService {
    @Autowired
    AdminRoleRepository repo;
    @Override
    public AdminRole getById(Long id) {
        return repo.findById(id).orElse(null);
    }
	@Override
	public AdminRole getByRoleId(String roleId) {
		return repo.findByRoleId(roleId);
	}
}
