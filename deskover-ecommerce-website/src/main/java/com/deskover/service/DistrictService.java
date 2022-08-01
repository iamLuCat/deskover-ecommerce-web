package com.deskover.service;

import java.util.List;

import com.deskover.model.entity.database.District;

public interface DistrictService {
	List<District> getByProvinceId(Long provinceId);

	District getById(Long id);
}
