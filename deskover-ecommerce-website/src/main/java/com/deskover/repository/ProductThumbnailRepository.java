package com.deskover.repository;

import com.deskover.entity.ProductThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductThumbnailRepository extends JpaRepository<ProductThumbnail, Long> {
}