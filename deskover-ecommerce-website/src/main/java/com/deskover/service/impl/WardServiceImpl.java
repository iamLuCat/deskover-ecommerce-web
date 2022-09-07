package com.deskover.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.entity.Ward;
import com.deskover.reponsitory.WardReponsitory;
import com.deskover.service.WardService;

@Service
public class WardServiceImpl implements WardService{
	@Autowired
	WardReponsitory repo;
	
	@Override
	public List<Ward> getByDistrictIdAndProvinceId(long districtId, Long provinceId) {
		return repo.findByDistrictIdAndProvinceId(districtId, provinceId);
	}

	@Override
	public Ward getById(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElse(null);
	}

}
