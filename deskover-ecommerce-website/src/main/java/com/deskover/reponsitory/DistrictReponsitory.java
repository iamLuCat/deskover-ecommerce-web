package com.deskover.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.District;

public interface DistrictReponsitory extends JpaRepository<District, Long> {
	List<District> findByProvinceId(Long provinceId);
}
