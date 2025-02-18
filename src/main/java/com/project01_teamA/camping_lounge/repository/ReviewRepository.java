package com.project01_teamA.camping_lounge.repository;

import com.project01_teamA.camping_lounge.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 조회수 증가
    @Modifying
    @Query(value = "UPDATE review SET review_hit = review_hit + 1 WHERE review_id = :reviewId", nativeQuery = true)
    void incrementReviewHit(@Param("reviewId") Long reviewId);

    // 좋아요 수 증가
    @Modifying
    @Query(value = "UPDATE review SET review_like = review_like + 1 WHERE review_id = :reviewId", nativeQuery = true)
    void incrementReviewLike(@Param("reviewId") Long reviewId);

    // 좋아요 수 감소 (예시로 추가)
    @Modifying
    @Query(value = "UPDATE review SET review_like = review_like - 1 WHERE review_id = :reviewId", nativeQuery = true)
    void decrementReviewLike(@Param("reviewId") Long reviewId);


}
