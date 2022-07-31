package com.deskover.service;

import java.util.List;

import com.deskover.model.entity.database.Ward;

public interface WardService {
	List<Ward> getByDistrictIdAndProvinceId(long districtId, Long provinceId);

	Ward getById(Long id);
}
