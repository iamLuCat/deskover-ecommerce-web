package com.deskover.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskover.entity.ShippingMethods;

public interface ShippingRepository extends JpaRepository<ShippingMethods, Long> {

}
