package com.deskover.repository;

import com.deskover.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    Administrator findByUsername(String username);
    Boolean existsAdministratorByUsername(String username);
}