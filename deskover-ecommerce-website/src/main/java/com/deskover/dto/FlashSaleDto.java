package com.deskover.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlashSaleDto {
	private String name;
	private Timestamp startDate;
	private Timestamp endDate;
}
