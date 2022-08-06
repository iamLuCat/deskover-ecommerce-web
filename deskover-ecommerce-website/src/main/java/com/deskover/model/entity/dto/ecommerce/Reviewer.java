package com.deskover.model.entity.dto.ecommerce;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.deskover.model.entity.database.Rating;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reviewer {
	public Reviewer(Page<Rating> rating) {
		this.totalPage = rating.getTotalPages();
		this.reviews = new ArrayList<RatingDTO>();
		rating.toList().stream().forEach(subcategory ->{
			RatingDTO ratingDTO = new RatingDTO(subcategory);
			reviews.add(ratingDTO);
		});
	}
	
	private int totalPage;
	private List<RatingDTO> reviews;
}
