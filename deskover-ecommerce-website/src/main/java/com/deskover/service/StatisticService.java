package com.deskover.service;

import com.deskover.model.entity.dto.TotalPrice;

public interface StatisticService {
	String[][] getTotalPricePerMonthAndYear(Integer months,Integer years);
	TotalPrice getToTalByCategory(String month, String year);
}
