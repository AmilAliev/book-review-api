package com.bookreview.api.service.impl;

import com.bookreview.api.dto.ReviewDto;
import com.bookreview.api.exceptions.BookNotFoundException;
import com.bookreview.api.exceptions.ReviewNotFoundException;
import com.bookreview.api.models.Book;
import com.bookreview.api.models.Review;
import com.bookreview.api.repository.BookRepository;
import com.bookreview.api.repository.ReviewRepository;
import com.bookreview.api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    @Override
    public ReviewDto createReview(long bookId, ReviewDto reviewDto) {
        log.info("Creating review for bookId={}", bookId);

        Review review = mapToEntity(reviewDto);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    log.error("Book not found for ID: {}", bookId);
                    return new BookNotFoundException("Book with associated review not found");
                });

        review.setBook(book);

        Review newReview = reviewRepository.save(review);
        log.info("Review created successfully id={}", newReview.getId());

        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewsByBookId(long bookId) {
        log.info("Fetching reviews for bookId={}", bookId);

        List<Review> reviews = reviewRepository.findByBookId(bookId);
        log.debug("Fetched {} reviews for bookId={}", reviews.size(), bookId);

        return reviews.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public ReviewDto getReviewById(int reviewId, long bookId) {
        log.info("Fetching reviewId={} for bookId={}", reviewId, bookId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    log.error("Book not found for ID: {}", bookId);
                    return new BookNotFoundException("Book with associated review not found");
                });

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    log.error("Review not found for ID: {}", reviewId);
                    return new ReviewNotFoundException("Review with associated book not found");
                });

        if (review.getBook().getId() != book.getId()) {
            log.warn("Review reviewId={} does NOT belong to bookId={}", reviewId, bookId);
            throw new ReviewNotFoundException("This review does not belong to this book");
        }

        log.info("Review successfully fetched reviewId={}", reviewId);
        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(long bookId, int reviewId, ReviewDto reviewDto) {
        log.info("Updating reviewId={} for bookId={}", reviewId, bookId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    log.error("Book not found for ID: {}", bookId);
                    return new BookNotFoundException("Book with associated review not found");
                });

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    log.error("Review not found for ID: {}", reviewId);
                    return new ReviewNotFoundException("Review with associated book not found");
                });

        if (review.getBook() == null || review.getBook().getId() != book.getId()) {
            log.warn("Attempt to update reviewId={} that does NOT belong to bookId={}", reviewId, bookId);
            throw new ReviewNotFoundException("This review does not belong to this book");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updatedReview = reviewRepository.save(review);

        log.info("Review updated successfully reviewId={}", reviewId);
        return mapToDto(updatedReview);
    }


    @Override
    public void deleteReviewById(long bookId, int reviewId) {
        log.info("Deleting reviewId={} for bookId={}", reviewId, bookId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    log.error("Book not found for ID: {}", bookId);
                    return new BookNotFoundException("Book with associated review not found");
                });

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    log.error("Review not found for ID: {}", reviewId);
                    return new ReviewNotFoundException("Review with associated book not found");
                });

        if (review.getBook().getId() != book.getId()) {
            log.warn("Attempt to delete reviewId={} that does NOT belong to bookId={}", reviewId, bookId);
            throw new ReviewNotFoundException("This review does not belong to this book");
        }

        reviewRepository.delete(review);
        log.info("Review deleted successfully reviewId={}", reviewId);
    }

    private ReviewDto mapToDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getTitle(),
                review.getContent(),
                review.getStars()
        );
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }
}
