package com.deskover.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    Administrator findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByUsername(Administrator admin);
    Page<Administrator> findByActived(Boolean isActive, Pageable pageable);
    List<Administrator> findByActived(Boolean isActive);
}