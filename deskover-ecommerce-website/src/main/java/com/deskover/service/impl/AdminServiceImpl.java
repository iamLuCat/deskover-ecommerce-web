package com.deskover.service.impl;

import com.deskover.dto.AdminCreateDto;
import com.deskover.dto.AdministratorDto;
import com.deskover.entity.AdminAuthority;
import com.deskover.entity.AdminPassword;
import com.deskover.entity.Administrator;
import com.deskover.repository.AdministratorRepository;
import com.deskover.service.AdminAuthorityService;
import com.deskover.service.AdminPasswordService;
import com.deskover.service.AdminRoleService;
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

    @Autowired
    private AdminRoleService adminRoleService;

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
            throw new IllegalArgumentException("Username này đã tồn tại");
        }
        Administrator entityAdmin = MapperUtil.map(admin, Administrator.class);
        entityAdmin.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        entityAdmin.setActived(Boolean.TRUE);
        entityAdmin.setAvatar(null);
        entityAdmin.setModifiedAt(null);
        entityAdmin.setModifiedUser(SecurityContextHolder.getContext().getAuthentication().getName());
        Administrator adminCreated = repo.save(entityAdmin);

        AdminPassword createPassword = new AdminPassword();
        createPassword.setAdmin(adminCreated);
        createPassword.setPassword(admin.getPassword());
        AdminPassword passwordCreated = adminPasswordService.create(createPassword);
        adminCreated.setPassword(passwordCreated);

        admin.getListRoleId().forEach(item -> {
            System.out.println(item);
            AdminAuthority authority = new AdminAuthority();
            authority.setAdmin(adminCreated);
            authority.setRole(adminRoleService.getById(item));
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
