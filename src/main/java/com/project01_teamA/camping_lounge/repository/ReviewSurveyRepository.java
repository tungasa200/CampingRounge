package com.project01_teamA.camping_lounge.repository;

import com.project01_teamA.camping_lounge.entity.Review;
import com.project01_teamA.camping_lounge.entity.ReviewSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewSurveyRepository extends JpaRepository<ReviewSurvey, Long> {
    Optional<ReviewSurvey> findByReview(Review review);
}