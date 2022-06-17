package com.deskover.dto.ghtk.response;

import java.io.Serializable;

import com.deskover.dto.ghtk.entity.FeeResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeeResponseData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8215042262200510345L;
	
	private FeeResponse fee;
}

