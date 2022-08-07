package com.deskover.model.entity.dto.ecommerce;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.deskover.model.entity.database.Rating;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reviewer {
	public Reviewer(Page<Rating> rating) {
		this.totalPage = rating.getTotalPages();
		this.reviews = rating.toList().stream().map(r -> new RatingDTO(r)).collect(Collectors.toList());
	}
	
	private int totalPage;
	private List<RatingDTO> reviews;
}
