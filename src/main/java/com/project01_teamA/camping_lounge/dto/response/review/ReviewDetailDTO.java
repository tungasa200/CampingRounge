package com.project01_teamA.camping_lounge.dto.response.review;

import com.project01_teamA.camping_lounge.entity.Review;
import com.project01_teamA.camping_lounge.entity.ReviewFiles;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ReviewDetailDTO {
    private Long reviewId;
    private String reviewTitle;
    private String reviewContent;
    private String reviewPostingDate;
    private String reviewEditingDate;
    private int reviewHit;
    private List<String> imageUrls;

    @Builder
    public ReviewDetailDTO(Long reviewId, String reviewTitle, String reviewContent, String reviewPostingDate, String reviewEditingDate, int reviewHit, List<String> imageUrls) {
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewPostingDate = reviewPostingDate;
        this.reviewEditingDate = reviewEditingDate;
        this.reviewHit = reviewHit;
        this.imageUrls = imageUrls;
    }

    public static ReviewDetailDTO fromEntity(Review review) {
        List<String> imageUrls = review.getReviewFiles().stream()
                .map(ReviewFiles::getStoredFileName)  // 저장된 파일 이름을 이미지 URL로 사용
                .collect(Collectors.toList());

        return ReviewDetailDTO.builder()
                .reviewId(review.getReviewId())
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .reviewPostingDate(review.getReviewPostingDate())
                .reviewEditingDate(review.getReviewPostingDate())  // 수정 날짜와 작성 날짜 동일하게 처리
                .reviewHit(review.getReviewHit())
                .imageUrls(imageUrls)  // 이미지 URL 리스트 추가
                .build();
    }
}