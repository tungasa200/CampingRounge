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
    private int reviewLike;
    private String reviewThumbImg;

    @Builder
    public ReviewListDTO(Long reviewId, String reviewTitle, String reviewContent, int reviewHit, int reviewLike, String reviewThumbImg) {
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewHit = reviewHit;
        this.reviewLike = reviewLike;
        this.reviewThumbImg = reviewThumbImg;
    }

    public static ReviewListDTO fromEntity(Review review){

        String reviewThumbImg = null;
        if (review.getReviewFiles() != null && !review.getReviewFiles().isEmpty()) {
            reviewThumbImg = review.getReviewFiles().get(0).getFilePath();
        }

        return ReviewListDTO.builder()
                .reviewId(review.getReviewId())
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .reviewHit(review.getReviewHit())
                .reviewLike(review.getReviewLike())
                .reviewThumbImg(reviewThumbImg)
                .build();
    }
}
