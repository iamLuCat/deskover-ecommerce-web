package com.deskover.model.entity.database.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.FlashSale;

public interface FlashSaleRepository extends JpaRepository<FlashSale, Long>{
	List<FlashSale> findAllByActived(Boolean isActive);
	FlashSale findFirstByActived(Boolean actived);
}
