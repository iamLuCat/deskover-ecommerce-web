package com.deskover.service;

import com.deskover.dto.ecommerce.FormReview;

public interface RatingService {
	public void postReview(FormReview formReview);

	Long totalRatings();
}
