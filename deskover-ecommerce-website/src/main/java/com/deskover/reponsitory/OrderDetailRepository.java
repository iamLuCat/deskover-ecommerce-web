package com.deskover.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deskover.entity.Order;
import com.deskover.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	
	OrderDetail  findByOrder(Order order);
	
	@Query(value = "select * from order_detail where tel = ?1", nativeQuery = true)
	List<OrderDetail> getByPhone(String phone);


	
}