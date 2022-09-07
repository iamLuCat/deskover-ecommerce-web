package com.deskover.reponsitory;

import com.deskover.dto.dashboard.OrderReport;
import com.deskover.dto.dashboard.ProductReport;
import com.deskover.entity.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	List<OrderItem> findByOrderId(Long id);

	@Query(value = "select sum(oi.quantity * oi.price) " +
			"from OrderItem oi " +
			"where oi.order.orderStatus.code = 'GH-TC'")
	Double getTotalRevenue();

	// Tổng số lượng hàng đã bán theo danh mục
	@Query(value = "select new OrderReport(oi.product.subCategory, sum(oi.quantity)) " +
			"from OrderItem oi " +
			"where oi.order.orderStatus.code = 'GH-TC'")
	List<OrderReport> getQuantityProductSoldBySubcategory();

	// Top 5 sản phẩm bán chạy nhất
	@Query(value = "select new ProductReport(oi.product, sum(oi.quantity)) " +
			"from OrderItem oi " +
			"where oi.order.orderStatus.code = 'GH-TC' " +
			"group by oi.product")
	List<ProductReport> getTopProductSold();
}