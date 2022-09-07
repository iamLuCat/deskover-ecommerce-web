package com.deskover.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.Users;
import com.deskover.entity.Verify;

public interface VerifyRepository extends JpaRepository<Verify, Long> {
    Verify findByToken(String token);

    Verify findByUser(Users user);
	
}