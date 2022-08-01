package com.deskover.model.entity.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.ProductThumbnail;

public interface ProductThumbnailRepository extends JpaRepository<ProductThumbnail, Long> {
}