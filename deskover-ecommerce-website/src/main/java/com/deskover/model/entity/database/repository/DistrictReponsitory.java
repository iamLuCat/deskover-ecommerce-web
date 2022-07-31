package com.deskover.model.entity.database.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.District;

public interface DistrictReponsitory extends JpaRepository<District, Long> {
	List<District> findByProvinceId(Long provinceId);
}
