package com.deskover.dto.ecommerce;

import java.text.SimpleDateFormat;

import com.deskover.entity.Rating;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDTO {
	
	public RatingDTO(Rating rating) {
		this.point = rating.getPoint();
		this.content = rating.getContent();
		this.poster = rating.getFullname();
		
		this.time =  new SimpleDateFormat("dd/MM/yyyy").format(rating.getModifiedAt());
	}
	
	private String poster;
	private Integer point;
	private String content;
	private String time;
}
