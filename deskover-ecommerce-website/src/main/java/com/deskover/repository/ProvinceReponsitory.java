package com.deskover.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.Province;

public interface ProvinceReponsitory extends JpaRepository<Province, Long> {
	List<Province> findAll();
}
