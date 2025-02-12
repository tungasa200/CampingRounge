package com.project01_teamA.camping_lounge.dto.request.review;

import com.project01_teamA.camping_lounge.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewUpdateDTO {
    private String reviewTitle;
    private String reviewContent;

    @Builder
    public ReviewUpdateDTO(String reviewTitle, String reviewContent) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
    }

    public static ReviewUpdateDTO fromEntity(Review review){
        return ReviewUpdateDTO.builder()
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .build();
    }


}
