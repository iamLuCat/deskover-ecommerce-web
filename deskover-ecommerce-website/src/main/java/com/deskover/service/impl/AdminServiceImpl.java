package com.deskover.service.impl;

import com.deskover.entity.Administrator;
import com.deskover.repository.AdministratorRepository;
import com.deskover.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdministratorRepository repo;

    @Override
    public Administrator getById(Long id) {
        return repo.getById(id);
    }

    @Override
    public Administrator getByUsername(String username) {
        return repo.findByUsername(username);
    }
}
