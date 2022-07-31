package com.deskover.model.entity.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.model.entity.database.ShippingMethods;

public interface ShippingRepository extends JpaRepository<ShippingMethods, Long> {

}
