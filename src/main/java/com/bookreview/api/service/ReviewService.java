package com.bookreview.api.service;

import com.bookreview.api.dto.ReviewDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ReviewService {
    ReviewDto createReview(long bookId, ReviewDto reviewDto);
    List<ReviewDto> getReviewsByBookId(long bookId);
    ReviewDto getReviewById(int reviewId,long bookId);
}
