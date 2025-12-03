package com.bookreview.api.controllers;

import com.bookreview.api.dto.ReviewDto;
import com.bookreview.api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{bookId}/review")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "bookId") int bookId,
                                                  @RequestBody ReviewDto reviewDto) {
        log.info("POST /api/book/{}/review called", bookId);
        log.debug("Request body: {}", reviewDto);

        ReviewDto created = reviewService.createReview(bookId, reviewDto);

        log.info("Review created for bookId={}", bookId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviews(@PathVariable int bookId) {
        log.info("GET /api/book/{}/reviews called", bookId);

        List<ReviewDto> reviews = reviewService.getReviewsByBookId(bookId);

        log.debug("Fetched {} reviews for bookId={}", reviews.size(), bookId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{bookId}/reviews/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "bookId") int bookId,
                                                   @PathVariable(value = "id") int reviewId) {
        log.info("GET /api/book/{}/reviews/{} called", bookId, reviewId);

        ReviewDto review = reviewService.getReviewById(bookId, reviewId);

        log.debug("Fetched review: {}", review);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PutMapping("/{bookId}/reviews/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(value = "bookId") int bookId,
                                                  @PathVariable(value = "id") int reviewId,
                                                  @RequestBody ReviewDto reviewDto) {
        log.info("PUT /api/book/{}/reviews/{} called", bookId, reviewId);
        log.debug("Update body: {}", reviewDto);

        ReviewDto updateReview = reviewService.updateReview(bookId, reviewId, reviewDto);

        log.info("Review updated: id={}", reviewId);
        return new ResponseEntity<>(updateReview, HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "bookId") long bookId,
                                               @PathVariable(value = "id") int reviewId) {
        log.info("DELETE /api/book/{}/reviews/{} called", bookId, reviewId);

        reviewService.deleteReviewById(bookId, reviewId);

        log.warn("Review deleted: id={}", reviewId);

        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }
}
