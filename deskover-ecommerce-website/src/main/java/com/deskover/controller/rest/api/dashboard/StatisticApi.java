package com.deskover.controller.rest.api.dashboard;

import com.deskover.model.entity.dto.TotalPrice;
import com.deskover.service.ProductService;
import com.deskover.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin/dashboard")
public class StatisticApi {
	
	@Autowired
	private StatisticService statisticService;

	@Autowired
	private ProductService productService;

	// DashBoard-ADMIN
	@GetMapping("/total-order-revenue")
	public ResponseEntity<?> getTotalPricePerMonthAndYear(@RequestParam("month") Integer month, @RequestParam("year") Integer year){
		return ResponseEntity.ok(statisticService.getTotalPricePerMonthAndYear(month, year));
	}

	@GetMapping("/total-order-revenue-by-category")
	public TotalPrice getTotalByCategory(@RequestParam("month") String month) {
		return statisticService.getTotalByCategory(month,"2022");
	}

	@GetMapping("/general-report")
	public ResponseEntity<?> getTotalGeneral() {
		return ResponseEntity.ok(statisticService.getTotalGeneral());
	}
	@GetMapping("/product-report")
	public ResponseEntity<?> getTotalProductByCategory() {
//		return ResponseEntity.ok(productService.totalQuantityProductByCategory());
		return ResponseEntity.ok(statisticService.getQuantityProductSoldBySubcategory());
	}

	@GetMapping("/top-product-sold")
	public ResponseEntity<?> getTop5ProductSold(@RequestParam Optional<Integer> limit) {
		return ResponseEntity.ok(statisticService.getTopProductSold(limit.orElse(5)));
	}

}
