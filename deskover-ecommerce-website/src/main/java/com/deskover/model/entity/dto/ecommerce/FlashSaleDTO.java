package com.deskover.model.entity.dto.ecommerce;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.deskover.model.entity.database.FlashSale;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlashSaleDTO {
	
	public FlashSaleDTO(FlashSale fs) {
		this.end = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(fs.getEndDate());
	}
	
	private String end;
	private List<Item> items;
}
