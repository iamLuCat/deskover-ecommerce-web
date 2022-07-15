package com.deskover.service.impl;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.dto.AdminCreateDto;
import com.deskover.dto.AdministratorDto;
import com.deskover.entity.AdminAuthority;
import com.deskover.entity.Administrator;
import com.deskover.repository.AdministratorRepository;
import com.deskover.service.AdminAuthorityService;
import com.deskover.service.AdminRoleService;
import com.deskover.service.AdminService;
import com.deskover.util.MapperUtil;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdministratorRepository repo;

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
    public Administrator getPrincipal(String username) {
    	return repo.findByUsername(username);
    }

    @Override
    @Transactional
    public AdministratorDto create(AdminCreateDto adminRequest) {
        if (repo.existsByUsername(adminRequest.getUsername())) {
            throw new IllegalArgumentException("Username này đã tồn tại");
        }
        
        Administrator entityAdmin = MapperUtil.map(adminRequest, Administrator.class);
        entityAdmin.setActived(Boolean.TRUE);
        entityAdmin.setAvatar(null);
        entityAdmin.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        entityAdmin.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        Administrator adminCreated = repo.save(entityAdmin);

        
        AdminAuthority defaultAuthority = new AdminAuthority();
        defaultAuthority.setAdmin(adminCreated);
        defaultAuthority.setRole(adminRoleService.getById(Long.valueOf(3)));
        AdminAuthority authority = adminAuthorityService.create(defaultAuthority);
        Set<AdminAuthority> authorities = new LinkedHashSet<AdminAuthority>();
        authorities.add(authority);
        adminCreated.setAuthorities(authorities);
        
//        admin.getListRoleId().forEach(item -> {
//            System.out.println(item);
//            AdminAuthority authority = new AdminAuthority();
//            authority.setAdmin(adminCreated);
//            authority.setRole(adminRoleService.getById(item));
//            adminAuthorityService.create(authority);
//        });

        return MapperUtil.map(entityAdmin, AdministratorDto.class);
    }

    @Override
    @Transactional
    public AdministratorDto update(AdministratorDto adminUpdate) {
    	if (this.existsUsername(adminUpdate)) {
            throw new IllegalArgumentException("Username này đã tồn tại");
        }
    	
    	adminUpdate.setModifiedAt(new Timestamp(System.currentTimeMillis()));
    	adminUpdate.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
    	
    	Administrator entityAdmin = MapperUtil.map(adminUpdate, Administrator.class);
    	// entityAdmin.setPassword(MapperUtil.map(adminUpdate.getPassword(), AdminPassword.class));

//    	repoPass.saveAndFlush(entityAdmin.getPassword());
    	
    	Administrator adminUpdated = repo.saveAndFlush(entityAdmin);
 
        return MapperUtil.map(adminUpdated, AdministratorDto.class);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Boolean existsUsername(String username) {
        return repo.existsByUsername(username);
    }

    @Override
    public Boolean existsUsername(AdministratorDto adminUpdate) {
        Administrator adminExists = repo.findByUsername(adminUpdate.getUsername());
        return (adminExists.getUsername() != null && !adminExists.getId().equals(adminUpdate.getId()));
    }
}
