package com.deskover.service;

import com.deskover.model.entity.dto.TotalPrice;
import com.deskover.model.entity.dto.dashboard.OrderReport;
import com.deskover.model.entity.dto.dashboard.ProductReport;

import java.util.List;
import java.util.Map;

public interface StatisticService {
	String[][] getTotalPricePerMonthAndYear(Integer months,Integer years);
	TotalPrice getTotalByCategory(String month, String year);
	Map<String, Object> getTotalGeneral();
	List<OrderReport> getQuantityProductSoldBySubcategory();
	List<ProductReport> getTopProductSold(Integer limit);
    List<Object> getTotalAccountByRole();
}
