package com.bookreview.api.repository;

import com.bookreview.api.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Integer> {

    List<Review> findByBookId(long bookId);
}
