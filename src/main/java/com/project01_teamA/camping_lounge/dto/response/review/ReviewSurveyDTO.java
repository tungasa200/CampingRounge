package com.project01_teamA.camping_lounge.dto.response.review;

import com.project01_teamA.camping_lounge.entity.ReviewSurvey;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewSurveyDTO {

    private int surveySatisfaction;
    private int surveySiteSize;
    private int surveyCleanStatus;
    private int surveyKindness;

    @Builder
    public ReviewSurveyDTO(int surveySatisfaction, int surveySiteSize, int surveyCleanStatus, int surveyKindness) {
        this.surveySatisfaction = surveySatisfaction;
        this.surveySiteSize = surveySiteSize;
        this.surveyCleanStatus = surveyCleanStatus;
        this.surveyKindness = surveyKindness;
    }

    public static ReviewSurveyDTO fromEntity (ReviewSurvey reviewSurvey){
        return ReviewSurveyDTO.builder()
                .surveySatisfaction(reviewSurvey.getSurveySatisfaction())
                .surveySiteSize(reviewSurvey.getSurveySiteSize())
                .surveyCleanStatus(reviewSurvey.getSurveyCleanStatus())
                .surveyKindness(reviewSurvey.getSurveyKindness())
                .build();
    }
}
