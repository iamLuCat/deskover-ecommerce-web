package com.deskover.model.entity.database.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.Ward;

public interface WardReponsitory extends JpaRepository<Ward, Long> {
	List<Ward> findByDistrictIdAndProvinceId(Long districtId, Long provinceId);
}
