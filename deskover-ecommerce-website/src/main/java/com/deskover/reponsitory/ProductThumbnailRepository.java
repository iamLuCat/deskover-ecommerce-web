package com.deskover.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.ProductThumbnail;

public interface ProductThumbnailRepository extends JpaRepository<ProductThumbnail, Long> {
}