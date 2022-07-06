package com.deskover.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.deskover.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query(value = "{CALL getTotalPrice_Shiping_PerMonth(:month, :year,:modified_by)}", nativeQuery = true)
	String getToTalPricePerMonth(@Param("month") String month,
			@Param("year") String year,
			@Param("modified_by") String modified_by);
	
	@Procedure(name = "Order.getTotalPrice_Shiping_PerMonth",outputParameterName = "total")
	String getTotalCarsByModelEntiy(@Param("month") String month,
			@Param("year") String year,
			@Param("modified_by") String modified_by,
			@Param("total") String total);
}