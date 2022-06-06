package com.deskover.dto.GHTKDto.reponse;

import java.io.Serializable;

import com.deskover.dto.GHTKDto.entity.FeeReponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeeReponseData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8215042262200510345L;
	
	private FeeReponse fee;
}

