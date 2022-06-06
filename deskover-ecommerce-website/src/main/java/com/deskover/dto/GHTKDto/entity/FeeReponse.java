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
public class FeeReponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1037548194366540286L;
	
	private int fee;
	
	private boolean delivery;
}
