package com.deskover.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.Ward;

public interface WardReponsitory extends JpaRepository<Ward, Long> {
	List<Ward> findByDistrictIdAndProvinceId(Long districtId, Long provinceId);
}
