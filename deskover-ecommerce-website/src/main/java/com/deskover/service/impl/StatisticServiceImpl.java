package com.deskover.service.impl;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskover.dto.TotalPrice;
import com.deskover.repository.OrderRepository;
import com.deskover.service.StatisticService;

@Service
public class StatisticServiceImpl implements StatisticService {
	
	@Autowired
	private OrderRepository repository;

	@Override
	public TotalPrice getToTalByCategory(String month, String year) {
		
		List<Object[]> totalByCategories =repository.getToTalByCategory(month,year);
		
		List<String> array1 = new ArrayList<>();
		List<Double> array2 = new ArrayList<>();
		
		totalByCategories.forEach((total)->{
			array1.add(total[0].toString());
			array2.add((Double) total[1]);

		});
		TotalPrice price = new TotalPrice(); 
		price.setName(array1);
		price.setTotalPrice(array2);
		return price;
	}

	@Override
	public String[][] getTotalPricePerMonthAndYear(Integer months,Integer years) {
		
		
		YearMonth current = YearMonth.now();
		if(years.equals(current.getYear())) {
			String[][] result = new String[2][months];
			for (int i = 0; i < months; i++) {
				String month = current.minusMonths((long)i).getMonthValue() + "";
				String year = current.minusMonths((long)i).getYear() + "";
				System.out.println(year);
				result[0][(months-1)-i] = month + "-" + year;
				result[1][(months-1)-i] = repository.getTotalPricePerYear(month, year);
			}
			return result;
		}
		String[][] result = new String[2][12];
		for (int i = 0; i < 12; i++) {
			result[0][(12-1)-i] = 12-i + "-" + years;
			result[1][(12-1)-i] = repository.getTotalPricePerYear(12-i+"", years+"");
		}
 		return result;
	}
	

}
