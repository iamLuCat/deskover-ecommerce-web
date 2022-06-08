package com.deskover.repository;

import com.deskover.entity.AdminPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminPasswordRepository extends JpaRepository<AdminPassword, Long> {
}