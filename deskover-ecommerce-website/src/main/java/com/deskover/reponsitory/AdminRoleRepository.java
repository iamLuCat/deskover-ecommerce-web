package com.deskover.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.AdminRole;

public interface AdminRoleRepository extends JpaRepository<AdminRole, Long> {
	AdminRole findByRoleId(String roleId);
}