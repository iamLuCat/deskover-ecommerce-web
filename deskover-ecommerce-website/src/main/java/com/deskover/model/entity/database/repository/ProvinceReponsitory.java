package com.deskover.model.entity.database.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.Province;

public interface ProvinceReponsitory extends JpaRepository<Province, Long> {
	List<Province> findAll();
}
