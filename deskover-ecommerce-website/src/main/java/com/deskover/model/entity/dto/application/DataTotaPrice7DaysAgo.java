package com.deskover.model.entity.dto.application;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataTotaPrice7DaysAgo implements Serializable {
	private static final long serialVersionUID = 7253659434612399184L;
	
	private List<Total7DaysAgo> data;

}
