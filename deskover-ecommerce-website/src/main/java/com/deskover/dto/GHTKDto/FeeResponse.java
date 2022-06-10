package com.deskover.dto.GHTKDto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeeResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1037548194366540286L;
	
	private int fee;
	
	private boolean delivery;
}
