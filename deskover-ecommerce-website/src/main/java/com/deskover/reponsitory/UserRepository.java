package com.deskover.reponsitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deskover.entity.Users;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
	List<Users> findAll();
	Page<Users> findByActived(Boolean actived, Pageable Page);
	Users findByUsername(String username);
	Boolean existsByUsername(String username);
	Users findByUsernameOrEmailOrPhone(String username, String email, String phone);
	Users findByEmail(String email);
	Boolean existsByEmail(String email);

	@Query(value = "{CALL countUsersByRole()}", nativeQuery = true)
	List<Object> getTotalAccountByRole();
}