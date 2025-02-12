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

    @Column(name = "review_title", nullable = false)
    private String reviewTitle;

    @Column(name = "review_content")
    private String reviewContent;

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReviewFiles> reviewFiles;

    @Column(name = "review_hit", columnDefinition = "int default 0")
    private int reviewHit;

    @Builder
    public Review(Long reviewId, String reviewTitle, String reviewContent, int reviewHit, List<ReviewFiles> reviewFiles) {
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewHit = reviewHit;
        this.reviewFiles = reviewFiles;
    }

    // 조회 수 증가
    public void upViewCount() {
        this.reviewHit++;
    }

    // 수정
    public void update(String reviewTitle, String reviewContent) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
    }
}