package com.deskover.service.impl;

import com.deskover.dto.AdminCreateDto;
import com.deskover.dto.AdministratorDto;
import com.deskover.entity.AdminAuthority;
import com.deskover.entity.AdminPassword;
import com.deskover.entity.Administrator;
import com.deskover.repository.AdministratorRepository;
import com.deskover.service.AdminAuthorityService;
import com.deskover.service.AdminPasswordService;
import com.deskover.service.AdminService;
import com.deskover.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdministratorRepository repo;

    @Autowired
    private AdminPasswordService adminPasswordService;

    @Autowired
    private AdminAuthorityService adminAuthorityService;

    @Override
    public Administrator getById(Long id) {
        return repo.getById(id);
    }

    @Override
    public Administrator getByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public AdministratorDto getPrincipal() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return MapperUtil.map(repo.findByUsername(username), AdministratorDto.class);
    }

    @Override
    @Transactional
    public Administrator create(AdminCreateDto admin) {
        if (repo.existsAdministratorByUsername(admin.getUsername())) {
            return null;
        }
        String password = admin.getPassword();
        Administrator entityAdmin = MapperUtil.map(admin, Administrator.class);
        entityAdmin.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        entityAdmin.setActived(Boolean.TRUE);
        entityAdmin.setAvatar(null);
        entityAdmin.setModifiedAt(null);
        entityAdmin.setModifiedAt(null);
        entityAdmin.setPassword(null);
        Administrator adminCreated = repo.save(entityAdmin);
        AdminPassword createPassword = new AdminPassword();
        createPassword.setAdmin(adminCreated);
//        createPassword.setPassword(password);
        AdminPassword passwordCreated = adminPasswordService.create(createPassword);
        adminCreated.setPassword(passwordCreated);
        admin.getListRole().forEach(item -> {
            AdminAuthority authority = new AdminAuthority();
            authority.setAdmin(adminCreated);
            authority.setRole(item);
            adminAuthorityService.create(authority);
        });
        return entityAdmin;
    }

    @Override
    @Transactional
    public AdministratorDto update(AdministratorDto admin) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Boolean existsUsername(String username) {
        return repo.existsAdministratorByUsername(username);
    }
}
