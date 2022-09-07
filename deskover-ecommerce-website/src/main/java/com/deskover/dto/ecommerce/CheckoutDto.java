package com.deskover.dto.ecommerce;

import java.util.ArrayList;

import com.deskover.dto.ProductDto;
import com.deskover.entity.UserAddress;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CheckoutDto {
	ArrayList<ProductDto> items;
	UserAddress entity;
	String total;
}
