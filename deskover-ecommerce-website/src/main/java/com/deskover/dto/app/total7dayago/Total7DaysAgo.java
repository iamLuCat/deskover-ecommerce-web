package com.deskover.dto.app.total7dayago;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Total7DaysAgo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8639077289009627168L;
	
	private String date;
	private Double totalPrice;
	private String priceFormat;
	
}
