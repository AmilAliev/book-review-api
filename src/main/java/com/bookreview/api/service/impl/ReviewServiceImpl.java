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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    @Override
    public ReviewDto createReview(long bookId, ReviewDto reviewDto) {

        Review review = mapToEntity(reviewDto);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book with associated review not found"));
        review.setBook(book);

        Review newReview = reviewRepository.save(review);
        return mapToDto(newReview);

    }

    @Override
    public List<ReviewDto> getReviewsByBookId(long bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);

        return reviews.stream()
                .map(review -> mapToDto(review))
                .toList();
    }

    @Override
    public ReviewDto getReviewById(int reviewId, long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associated book not found"));

        if (review.getBook().getId() != book.getId()) {
            throw new ReviewNotFoundException("This review does not belong to this book");
        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(long bookId, int reviewId, ReviewDto reviewDto) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associated book not found"));

        if (review.getBook().getId() != book.getId()) {
            throw new ReviewNotFoundException("This review does not belong to this book");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updatedReview = reviewRepository.save(review);

        return mapToDto(updatedReview);
    }

    @Override
    public void deleteReviewById(long bookId, int reviewId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associated book not found"));

        if (review.getBook().getId() != book.getId()) {
            throw new ReviewNotFoundException("This review does not belong to this book");
        }

        reviewRepository.delete(review);
    }


    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
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
