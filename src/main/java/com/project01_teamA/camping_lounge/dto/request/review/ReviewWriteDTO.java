package com.project01_teamA.camping_lounge.dto.request.review;

import com.project01_teamA.camping_lounge.entity.Review;
import com.project01_teamA.camping_lounge.entity.ReviewSurvey;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ReviewWriteDTO {

    private Long reviewId;
    private String reviewTitle;
    private String reviewContent;

    private int surveySatisfaction;
    private int surveySiteSize;
    private int surveyCleanStatus;
    private int surveyKindness;

    @Builder
    public ReviewWriteDTO(Long reviewId, String reviewTitle, String reviewContent, int surveySatisfaction, int surveySiteSize, int surveyCleanStatus, int surveyKindness) {
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.surveySatisfaction = surveySatisfaction;
        this.surveySiteSize = surveySiteSize;
        this.surveyCleanStatus = surveyCleanStatus;
        this.surveyKindness = surveyKindness;
    }

    public static ReviewWriteDTO fromEntity(Review review, ReviewSurvey reviewSurvey) {
        return ReviewWriteDTO.builder()
                .reviewId(review.getReviewId())
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .surveySatisfaction(reviewSurvey.getSurveySatisfaction())
                .surveySiteSize(reviewSurvey.getSurveySiteSize())
                .surveyCleanStatus(reviewSurvey.getSurveyCleanStatus())
                .surveyKindness(reviewSurvey.getSurveyKindness())
                .build();
    }

    public Review toEntity() {
        return Review.builder()
                .reviewTitle(this.reviewTitle)
                .reviewContent(this.reviewContent)
                .build();
    }
}
