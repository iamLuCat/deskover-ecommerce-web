package com.deskover.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.AdminAuthority;
import org.springframework.data.jpa.repository.Query;

public interface AdminAuthorityReponsitory extends JpaRepository<AdminAuthority, Long>{
	List<AdminAuthority> findByAdminId(Long id);
	AdminAuthority findByAdminIdAndRoleId(Long adminId, Long roleId);
	Boolean existsByAdminIdAndRoleId(Long adminId, Long roleId);
}
