package com.deskover.model.entity.dto.ecommerce;

import java.util.ArrayList;
import com.deskover.model.entity.database.UserAddress;
import com.deskover.model.entity.dto.ProductDto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CheckoutDto {
	ArrayList<ProductDto> items;
	UserAddress entity;
	String total;
}
