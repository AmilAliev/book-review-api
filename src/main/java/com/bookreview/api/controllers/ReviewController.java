package com.bookreview.api.controllers;

import com.bookreview.api.dto.ReviewDto;
import com.bookreview.api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("book/{bookId}/review")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "bookId") int bookId,
                                                  @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.createReview(bookId, reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("book/{bookId}/reviews")
    public List<ReviewDto> getReviews(@PathVariable(value = "bookId") int bookId) {
        return reviewService.getReviewsByBookId(bookId);
    }

    @GetMapping("book/{bookId}/reviews/{id} ")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "bookId") int bookId,
                                                   @PathVariable(value = "id") int reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(bookId, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PutMapping("book/{bookId}/reviews/{id} ")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(value = "bookId") int bookId,
                                                  @PathVariable(value = "id") int reviewId,
                                                  @RequestBody ReviewDto reviewDto) {
        ReviewDto updateReview = reviewService.updateReview(bookId, reviewId, reviewDto);
        return new ResponseEntity<>(updateReview, HttpStatus.OK);
    }

    @DeleteMapping("book/{bookId}/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "bookId") long bookid,
                                               @PathVariable(value = "id") int reviewid) {
        reviewService.deleteReviewById(bookid, reviewid);
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }
}
