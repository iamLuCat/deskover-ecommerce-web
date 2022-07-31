package com.deskover.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.model.entity.database.AdminRole;
import com.deskover.model.entity.database.repository.AdminRoleRepository;
import com.deskover.service.AdminRoleService;

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
