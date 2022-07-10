package com.deskover.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deskover.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	//Shipper
	@Query(value = "{CALL getTotalPrice_Shiping_PerMonth(:month, :year,:modified_by)}", nativeQuery = true)
	String getToTalPricePerMonth(@Param("month") String month,
			@Param("year") String year,
			@Param("modified_by") String modified_by);
	
	@Query(value = "{CALL countOrder(:month, :year,:modified_by)}", nativeQuery = true)
	String getCountOrder(@Param("month") String month,
			@Param("year") String year,
			@Param("modified_by") String modified_by);
	
	//Tổng tiền đơn hàng
	
	@Query(value = "{CALL totalOrder(:id)}", nativeQuery = true)
	Double getTotalOrder(@Param("id") Long id);
	
	
	//DashBoard-Admin
	@Query(value="{CALL totalByNameCategory(:month, :year)}", nativeQuery = true)
	String[] totalByNameCategory(@Param("month") String month,@Param("year") String year);
	
	@Query(value="{CALL totalPriceByCategory(:month, :year)}", nativeQuery = true)
	String[] totalPriceByCategory(@Param("month") String month,@Param("year") String year);
	
	List<Order> findByOrderStatusCode(String code);
	
	Order findByOrderCodeAndOrderStatusCode( String orderCode,  String status);
}