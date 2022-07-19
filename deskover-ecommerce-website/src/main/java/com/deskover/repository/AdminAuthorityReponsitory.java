package com.deskover.repository;

import com.deskover.entity.AdminAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminAuthorityReponsitory extends JpaRepository<AdminAuthority, Long>{
	@Query("select a from AdminAuthority a where a.admin.id = ?1")
	List<AdminAuthority> findByAdminId(Long id);
}
