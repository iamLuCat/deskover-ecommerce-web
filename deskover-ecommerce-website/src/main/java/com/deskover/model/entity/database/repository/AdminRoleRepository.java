package com.deskover.model.entity.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.AdminRole;

public interface AdminRoleRepository extends JpaRepository<AdminRole, Long> {
	AdminRole findByRoleId(String roleId);
}