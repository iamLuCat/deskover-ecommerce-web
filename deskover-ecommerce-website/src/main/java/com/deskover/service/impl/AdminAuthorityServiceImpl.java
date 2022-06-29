package com.deskover.service.impl;

import com.deskover.dto.AdminAuthorityDto;
import com.deskover.entity.AdminAuthority;
import com.deskover.repository.AdminAuthorityReponsitory;
import com.deskover.service.AdminAuthorityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminAuthorityServiceImpl implements AdminAuthorityService {
    @Autowired
    AdminAuthorityReponsitory repo;

    @Autowired
    ModelMapper mapper;

    @Override
    public Set<AdminAuthorityDto> getByAdminId(Long id) {
        List<AdminAuthority> list = repo.findByAdminId(id);
        Set<AdminAuthorityDto> set = new HashSet<>();
        list.forEach(item -> {
            AdminAuthorityDto authorityDto = mapper.map(item, AdminAuthorityDto.class);
            set.add(authorityDto);
        });
        return set;
    }

    @Override
    @Transactional
    public AdminAuthority create(AdminAuthority adminAuthority) {
        if(adminAuthority == null){
            throw new IllegalArgumentException("Thêm mới authority không thành công");
        }
        adminAuthority.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return repo.save(adminAuthority);
    }

}
