package com.project01_teamA.camping_lounge.dto.response.review;

import com.project01_teamA.camping_lounge.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewListDTO {
    private Long reviewId;
    private String reviewTitle;
    private String reviewContent;
    private int reviewHit;

    @Builder
    public ReviewListDTO(Long reviewId, String reviewTitle, String reviewContent, int reviewHit) {
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewHit = reviewHit;
    }

    public static ReviewListDTO fromEntity(Review review){
        return ReviewListDTO.builder()
                .reviewId(review.getReviewId())
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .reviewHit(review.getReviewHit())
                .build();
    }
}
