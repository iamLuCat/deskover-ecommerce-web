package com.deskover.service.impl;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deskover.dto.AdminCreateDto;
import com.deskover.dto.AdminUpdatePassDto;
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
	private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    @Autowired
    private AdministratorRepository repo;

    @Autowired
    private AdminAuthorityService adminAuthorityService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Override
    public Administrator getById(Long id) {
    	Administrator admin = repo.findById(id).orElse(null);
    	admin.setAuthorities(adminAuthorityService.getByAdminId(admin.getId()));
    	return admin;
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
    public AdministratorDto getPrincipal(String username) {
        return MapperUtil.map(repo.findByUsername(username), AdministratorDto.class);
    }

    @Override
    @Transactional
    public AdministratorDto create(AdminCreateDto adminRequest) {
        if (repo.existsByUsername(adminRequest.getUsername())) {
            throw new IllegalArgumentException("Username này đã tồn tại");
        }
        
        Administrator entityAdmin = MapperUtil.map(adminRequest, Administrator.class);
        entityAdmin.setActived(Boolean.TRUE);
        entityAdmin.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        entityAdmin.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        Administrator adminCreated = repo.save(entityAdmin);

        AdminAuthority defaultAuthority = new AdminAuthority();
        defaultAuthority.setAdmin(adminCreated);
        defaultAuthority.setRole(adminRoleService.getByRoleId("ROLE_STAFF"));
        AdminAuthority authorityDefault = adminAuthorityService.create(defaultAuthority);
        Set<AdminAuthority> authorities = new LinkedHashSet<AdminAuthority>();
        authorities.add(authorityDefault);
        adminCreated.setAuthorities(authorities);
        
        return MapperUtil.map(entityAdmin, AdministratorDto.class);
    }

    @Override
    @Transactional
    public AdministratorDto update(AdministratorDto adminUpdate) {
    	if (this.existsUsername(adminUpdate)) {
            throw new IllegalArgumentException("Username này đã tồn tại");
        }
    	   	
    	Administrator entityAdmin = MapperUtil.map(adminUpdate, Administrator.class);
    	entityAdmin.setModifiedAt(new Timestamp(System.currentTimeMillis()));
    	entityAdmin.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
    	
    	Administrator adminUpdated = repo.saveAndFlush(entityAdmin);
        return MapperUtil.map(adminUpdated, AdministratorDto.class);
    }
    
    @Override
    @Transactional
	public AdministratorDto updatePassowrd(AdminUpdatePassDto adminUpdatePass) {
    	Administrator existsAdmin = this.getById(adminUpdatePass.getId());
    	if(!adminUpdatePass.getOldPassword().equals(existsAdmin.getPassword())) {
    		throw new IllegalArgumentException("Sai mật khẩu cũ");
    	}else {
    		if(adminUpdatePass.getNewPassword().equals(adminUpdatePass.getOldPassword())) {
    			throw new IllegalArgumentException("Mật khẩu mới không được trùng với mật khẩu cũ");
    		}else {
    			if(!adminUpdatePass.getConfirmPassword().equals(adminUpdatePass.getNewPassword())) {
    				throw new IllegalArgumentException("Mật khẩu xác nhận không khớp");
    			}else {
    				String hashPass = bcrypt.encode(adminUpdatePass.getConfirmPassword());
    				existsAdmin.setPassword(hashPass);
    				return MapperUtil.map(repo.saveAndFlush(existsAdmin), AdministratorDto.class);
    			}
    		}
    	}
	}

    @Override
    @Transactional
    public void changeActived(Long id) {
        Administrator currentAdmin = this.getById(id);
        if(currentAdmin == null){
            throw new IllegalArgumentException("Tài khoản admin này không tồn tại");
        }
        if(currentAdmin.getActived()){
        	currentAdmin.setActived(Boolean.FALSE);
        	currentAdmin.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        	currentAdmin.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            repo.saveAndFlush(currentAdmin);
        }else{
        	currentAdmin.setActived(Boolean.TRUE);
        	currentAdmin.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        	currentAdmin.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        	repo.saveAndFlush(currentAdmin);
        }
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
