package com.deskover.model.entity.database.repository;

import com.deskover.model.entity.database.OrderItem;
import com.deskover.model.entity.dto.dashboard.OrderReport;
import com.deskover.model.entity.dto.dashboard.ProductReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	List<OrderItem> findByOrderId(Long id);

	@Query(value = "select sum(oi.quantity * oi.price) " +
			"from OrderItem oi " +
			"where oi.order.orderStatus.code = :orderStatusCode")
	Double getTotalRevenue(String orderStatusCode);

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