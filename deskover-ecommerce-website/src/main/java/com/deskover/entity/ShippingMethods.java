package com.deskover.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipping_methods")
public class ShippingMethods implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6448172588629562272L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "shipping_id")
	private String shippingId;
	
	@Column(name ="name_shipping")
	private String name_shipping;
	

}
