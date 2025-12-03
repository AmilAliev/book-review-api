package com.bookreview.api.repository;

import com.bookreview.api.models.Book;
import com.bookreview.api.models.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void ReviewRepository_SaveAll_ReturnsSavedReview() {

        Review review = Review.builder().
                title("title").content("content").stars(5)
                .build();

        Review savedReview = reviewRepository.save(review);

        Assertions.assertNotNull(savedReview);
        Assertions.assertTrue(savedReview.getId() > 0);
    }

    @Test
    public void ReviewRepository_GetAll_ReturnsMoreThanOneReview() {

        Review review = Review.builder().
                title("title").content("content").stars(5)
                .build();

        Review review2 = Review.builder().
                title("title").content("content").stars(5)
                .build();

        reviewRepository.save(review);
        reviewRepository.save(review2);

        List<Review> reviews = reviewRepository.findAll();

        Assertions.assertNotNull(reviews);
        Assertions.assertEquals(2, reviews.size());

    }

    @Test
    public void ReviewRepository_FindById_ReturnsSavedReview() {

        Review review = Review.builder().
                title("title").content("content").stars(5)
                .build();

        Review savedReview = reviewRepository.save(review);

        Review reviewReturn = reviewRepository.findById(savedReview.getId()).orElse(null);

        Assertions.assertNotNull(reviewReturn);
        Assertions.assertEquals("title", reviewReturn.getTitle());
    }

    @Test
    public void ReviewRepository_UpdateReview_ReturnReview() {

        Review review = Review.builder().
                title("title").content("content").stars(5)
                .build();

        reviewRepository.save(review);

        Review reviewSave = reviewRepository.findById(review.getId()).get();
        reviewSave.setTitle("updated title");
        reviewSave.setContent("updated content");
        Review updatedReview = reviewRepository.save(reviewSave);

        Assertions.assertNotNull(updatedReview.getTitle());
        Assertions.assertNotNull(updatedReview.getContent());
    }

    @Test
    public void ReviewRepository_ReviewDelete_ReturnReviewIsEmpty() {

        Review review = Review.builder().
                title("title").content("content").stars(5)
                .build();

        reviewRepository.save(review);
        reviewRepository.deleteById(review.getId());

        Optional<Review> reviewOptional = reviewRepository.findById(review.getId());

        Assertions.assertTrue(reviewOptional.isEmpty());
    }
}
