package com.deskover.model.entity.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.Verify;

public interface VerifyRepository extends JpaRepository<Verify, Long> {
}