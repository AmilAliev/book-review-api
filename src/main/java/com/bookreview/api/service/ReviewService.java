package com.bookreview.api.service;

import com.bookreview.api.dto.ReviewDto;

import java.util.List;


public interface ReviewService {
    ReviewDto createReview(long bookId, ReviewDto reviewDto);

    List<ReviewDto> getReviewsByBookId(long bookId);

    ReviewDto getReviewById(int reviewId, long bookId);

    ReviewDto updateReview(long bookId, int reviewId, ReviewDto reviewDto);

    void deleteReviewById(long bookId, int reviewId);
}
