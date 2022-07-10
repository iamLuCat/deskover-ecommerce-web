package com.deskover.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deskover.dto.TotalByCategory;
import com.deskover.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	//Shipper
	@Query(value = "{CALL getTotalPrice_Shiping_PerMonth(:month, :year,:modified_by)}", nativeQuery = true)
	String getToTalPricePerMonth(@Param("month") String month,
			@Param("year") String year,
			@Param("modified_by") String modified_by);
	
	//DashBoard-Admin
	@Query(value="{CALL totalByCategory(:month, :year)}", nativeQuery = true)
	String getToTalByCategory(@Param("month") String month,@Param("year") String year);
	
	List<Order> findByOrderStatusCode(String code);
	
	Order findByOrderCodeAndOrderStatusCode( String orderCode,  String status);
}