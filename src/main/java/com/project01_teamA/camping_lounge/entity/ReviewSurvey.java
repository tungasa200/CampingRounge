package com.project01_teamA.camping_lounge.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ReviewSurvey {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camp_id")
    private Campsite campsite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "survey_satisfaction")
    private int surveySatisfaction;

    @Column(name = "survey_site_size")
    private int surveySiteSize;

    @Column(name = "survey_clean_status")
    private int surveyCleanStatus;

    @Column(name = "survey_kindness")
    private int surveyKindness;

    @Builder
    public ReviewSurvey(Long surveyId, Review review, Campsite campsite, Member member, int surveySatisfaction, int surveySiteSize, int surveyCleanStatus, int surveyKindness) {
        this.surveyId = surveyId;
        this.review = review;
        this.campsite = campsite;
        this.member = member;
        this.surveySatisfaction = surveySatisfaction;
        this.surveySiteSize = surveySiteSize;
        this.surveyCleanStatus = surveyCleanStatus;
        this.surveyKindness = surveyKindness;
    }
}