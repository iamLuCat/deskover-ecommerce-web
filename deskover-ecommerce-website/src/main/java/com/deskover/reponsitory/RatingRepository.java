package com.deskover.reponsitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deskover.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
	
	@Query(value = "SELECT r FROM Rating r "
			+ "WHERE r.product.slug = ?1 "
			+ "AND r.actived = 1 ",
			nativeQuery = false)
	Page<Rating> getRating(String slug ,Pageable Page);
	
}
