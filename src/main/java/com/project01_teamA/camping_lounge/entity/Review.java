package com.project01_teamA.camping_lounge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Review extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camp_id")
    private Campsite campsite;

    @Column(name = "review_title", nullable = false)
    private String reviewTitle;

    @Column(name = "review_content")
    private String reviewContent;

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReviewFiles> reviewFiles;

    @Column(name = "review_hit", columnDefinition = "int default 0")
    private int reviewHit;

    @Column(name = "review_like", columnDefinition = "int default 0")
    private int reviewLike;

    @Builder
    public Review(Long reviewId, Member member, Campsite campsite, String reviewTitle, String reviewContent, int reviewHit, List<ReviewFiles> reviewFiles, int reviewLike) {
        this.reviewId = reviewId;
        this.member = member;
        this.campsite = campsite;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewHit = reviewHit;
        this.reviewFiles = reviewFiles;
        this.reviewLike = reviewLike;
    }

    // 조회 수 증가
    public void incrementViewCount() {
        this.reviewHit++;
    }

    // 좋아요 수 증가
    public void incrementLikeCount() {
        this.reviewLike++;
    }

    // 좋아요 수 감소
    public void decrementLikeCount() {
        this.reviewLike--;
    }

    // 좋아요 토글
    public void toggleLike(boolean isLiked) {
        if (isLiked) {
            incrementLikeCount();
        } else {
            decrementLikeCount();
        }
    }

    // 수정
    public void update(String reviewTitle, String reviewContent) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
    }



}