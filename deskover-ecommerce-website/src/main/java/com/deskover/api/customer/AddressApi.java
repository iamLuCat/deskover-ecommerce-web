package com.deskover.api.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.entity.District;
import com.deskover.entity.Province;
import com.deskover.entity.Ward;
import com.deskover.service.DistrictService;
import com.deskover.service.ProvinceService;
import com.deskover.service.WardService;

@RestController
@RequestMapping("v0/client")
public class AddressApi {

	@Autowired
	ProvinceService provinceService;

	@Autowired
	DistrictService districtService;

	@Autowired
	WardService wardService;

	@GetMapping("/province")
	public ResponseEntity<?> getProvince() {
		try {
			List<Province> list = provinceService.getAll();
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/province/{id}")
	public ResponseEntity<?> getProvinceById(@PathVariable("id") Long id) {
		try {
			Province province = provinceService.getById(id);
			return ResponseEntity.ok(province);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/district")
	public ResponseEntity<?> getDistrict(@RequestParam("provinceId") Long proviceId) {
		try {
			List<District> list = districtService.getByProvinceId(proviceId);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/district/{id}")
	public ResponseEntity<?> getDistrictById(@PathVariable("id") Long id) {
		try {
			District district = districtService.getById(id);
			return ResponseEntity.ok(district);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/ward")
	public ResponseEntity<?> getWard(@RequestParam("provinceId") Long proviceId,@RequestParam("districtId") Long districtId) {
		try {
			List<Ward> list = wardService.getByDistrictIdAndProvinceId(districtId, proviceId);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/ward/{id}")
	public ResponseEntity<?> getWardtById(@PathVariable("id") Long id) {
		try {
			Ward ward = wardService.getById(id);
			return ResponseEntity.ok(ward);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
