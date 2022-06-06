package com.deskover.dto.GHTKDto.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressGhtk implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -1237864852386922332L;

	 private String pick_address_id;
	 
	 private String address;
	 
	 private String pick_tel;
	 
	 private String pick_name;
}
