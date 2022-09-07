package com.deskover.reponsitory;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.deskover.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByProductIdAndUserUsername(Long productId, String username);
	List<Cart> findByUserUsername(String username);
	
	@Query(value = "SELECT c FROM Cart c "
			+ "WHERE c.product.slug = ?1 "
			+ "AND c.user.username = ?2",
			nativeQuery = false)
	Cart findByProductSlugAndUserUsername(String slug, String username);
}