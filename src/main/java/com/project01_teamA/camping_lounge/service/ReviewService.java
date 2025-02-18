package com.project01_teamA.camping_lounge.service;

import com.project01_teamA.camping_lounge.dto.response.review.ReviewDetailDTO;
import com.project01_teamA.camping_lounge.dto.response.review.ReviewListDTO;
import com.project01_teamA.camping_lounge.dto.request.review.ReviewUpdateDTO;
import com.project01_teamA.camping_lounge.dto.request.review.ReviewWriteDTO;
import com.project01_teamA.camping_lounge.entity.Review;
import com.project01_teamA.camping_lounge.entity.ReviewSurvey;
import com.project01_teamA.camping_lounge.exception.ResourceNotFoundException;
import com.project01_teamA.camping_lounge.repository.MemberRepository;
import com.project01_teamA.camping_lounge.repository.ReviewRepository;
import com.project01_teamA.camping_lounge.repository.ReviewSurveyRepository;
import com.project01_teamA.camping_lounge.repository.camp.CampRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final CampRepository campsiteRepository;
    private final ReviewSurveyRepository reviewSurveyRepository;

    // List
    public Page<ReviewListDTO> getAll(Pageable pageable){
        Page<Review> page = reviewRepository.findAll(pageable);
        List<ReviewListDTO> list = page.stream().map(ReviewListDTO::fromEntity).toList();
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    // Write
    public ReviewWriteDTO write(ReviewWriteDTO reviewWriteDTO) {
        // 1. Review 엔티티 저장
        Review review = reviewWriteDTO.toEntity();
        Review savedReview = reviewRepository.save(review);

        // 2. ReviewSurvey 엔티티 저장
        ReviewSurvey reviewSurvey = ReviewSurvey.builder()
                .review(savedReview)  // Review와 연결
                .surveySatisfaction(reviewWriteDTO.getSurveySatisfaction())
                .surveySiteSize(reviewWriteDTO.getSurveySiteSize())
                .surveyCleanStatus(reviewWriteDTO.getSurveyCleanStatus())
                .surveyKindness(reviewWriteDTO.getSurveyKindness())
                .build();

        reviewSurveyRepository.save(reviewSurvey);

        // 3. DTO 반환 (Review와 ReviewSurvey 둘 다 포함)
        return ReviewWriteDTO.fromEntity(savedReview, reviewSurvey);
    }

    // detail
    public ReviewDetailDTO detail(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다. ID: " + reviewId));

        // ReviewSurvey를 ReviewId로 찾기
        ReviewSurvey reviewSurvey = reviewSurveyRepository.findByReview(review)
                .orElse(null); // 없을 수도 있으니까 null 허용

        review.incrementViewCount();
        return ReviewDetailDTO.fromEntity(review, reviewSurvey);
    }


    // delete
    public void delete(Long reviewId) {
//        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
//        if (reviewOptional.isPresent()) {
//            Review review = reviewOptional.get();
//            if (review.get회원().get비밀번호().equals(비밀번호)) {
//                reviewRepository.deleteById(reviewId);
//            } else {
//                throw new UnauthorizedAccessException("비밀번호가 일치하지 않습니다.");
//            }
//        } else {
//            throw new ReviewNotFoundException("해당 리뷰를 찾을 수 없습니다.");
//        }
        reviewRepository.deleteById(reviewId);
    }

    // update
    public ReviewDetailDTO update(Long reviewId, ReviewUpdateDTO reviewUpdateDTO) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "Review Id", String.valueOf(reviewId)));
        review.update(reviewUpdateDTO.getReviewTitle(), reviewUpdateDTO.getReviewContent());
        Review updatedReview = reviewRepository.save(review);
        return ReviewDetailDTO.fromEntity(updatedReview, null);
    }

    // like toggle
    public void toggleLike(Long reviewId, boolean isLiked) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다. ID: " + reviewId));

        // 좋아요 토글
        review.toggleLike(isLiked);

        // 변경된 리뷰 저장
        reviewRepository.save(review);
    }
}