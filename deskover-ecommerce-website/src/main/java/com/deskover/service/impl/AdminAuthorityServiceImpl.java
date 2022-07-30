package com.deskover.service.impl;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.entity.AdminAuthority;
import com.deskover.repository.AdminAuthorityReponsitory;
import com.deskover.service.AdminAuthorityService;
import com.deskover.service.AdminRoleService;
import com.deskover.service.AdminService;

@Service
public class AdminAuthorityServiceImpl implements AdminAuthorityService {
    @Autowired
    AdminAuthorityReponsitory repo;

    @Autowired
    AdminService adminService;
    
    @Autowired
    AdminRoleService roleService;
    
    @Autowired
    ModelMapper mapper;

    @Override
    public Set<AdminAuthority> getByAdminId(Long id) {
        List<AdminAuthority> list = repo.findByAdminId(id);
		Set<AdminAuthority> set = new LinkedHashSet<>();
        list.forEach(item -> {
            set.add(item);
        });
        return set;
    }

    @Override
    @Transactional
    public AdminAuthority create(AdminAuthority adminAuthority) {
        if(adminAuthority == null){
            throw new IllegalArgumentException("Thêm mới authority không thành công");
        }
        adminAuthority.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        adminAuthority.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return repo.save(adminAuthority);
    }

    @Override
    public AdminAuthority findByAdminIdAndRoleId(Long adminId, Long roleId) {
        return repo.findByAdminIdAndRoleId(adminId,roleId);
    }

    @Transactional
    @Override
    public void changeRole(Long adminId, Long roleId) {
        if(repo.existsByAdminIdAndRoleId(adminId,roleId)){
            AdminAuthority existsRole = this.findByAdminIdAndRoleId(adminId, roleId);
            repo.delete(existsRole);
        }else{
            AdminAuthority createRole = new AdminAuthority();
            createRole.setRole(roleService.getById(roleId));
            createRole.setAdmin(adminService.getById(adminId));
            createRole.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            createRole.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            repo.save(createRole);
        }
    }

}
