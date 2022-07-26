package com.deskover.service;

import java.util.List;

import com.deskover.entity.District;

public interface DistrictService {
	List<District> getByProvinceId(Long provinceId);
}
