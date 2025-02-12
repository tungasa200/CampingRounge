package com.project01_teamA.camping_lounge.dto.request.review;

import com.project01_teamA.camping_lounge.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ReviewWriteDTO {

    private String reviewTitle;
    private String reviewContent;

    @Builder
    public ReviewWriteDTO(String reviewTitle, String reviewContent) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
    }

    public static ReviewWriteDTO fromEntity(Review review) {
        return ReviewWriteDTO.builder()
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .build();
    }

    public Review toEntity() {
        return Review.builder()
                .reviewTitle(this.reviewTitle)
                .reviewContent(this.reviewContent)
                .build();
    }
}
