package com.deskover.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.dto.TotalPrice;
import com.deskover.service.StatisticService;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin")
public class StatisticApi {
	
	@Autowired
	private StatisticService statisticService;

	// DashBoard-ADMIN
	
	@GetMapping("/dashboard")
	public ResponseEntity<?> getTotalPricePerMonthAndYear(@RequestParam("month") Integer month, @RequestParam("year") Integer year){
		return ResponseEntity.ok(statisticService.getTotalPricePerMonthAndYear(month, year));
	}

	@GetMapping("/dashboard/category")
	public TotalPrice getTotalByCategory(@RequestParam("month") String month) {
		TotalPrice totalByCategories = statisticService.getToTalByCategory(month,"2022") ;
		return totalByCategories;
	}
	
}
