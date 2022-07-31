package com.deskover.model.entity.database.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.AdminAuthority;

public interface AdminAuthorityReponsitory extends JpaRepository<AdminAuthority, Long>{
	List<AdminAuthority> findByAdminId(Long id);
	AdminAuthority findByAdminIdAndRoleId(Long adminId, Long roleId);
	Boolean existsByAdminIdAndRoleId(Long adminId, Long roleId);
}
