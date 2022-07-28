package com.deskover.service;

import java.util.List;

import com.deskover.entity.Ward;

public interface WardService {
	List<Ward> getByDistrictIdAndProvinceId(long districtId, Long provinceId);

	Ward getById(Long id);
}
