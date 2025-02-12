package com.project01_teamA.camping_lounge.service;

import com.project01_teamA.camping_lounge.dto.response.review.ReviewDetailDTO;
import com.project01_teamA.camping_lounge.dto.response.review.ReviewListDTO;
import com.project01_teamA.camping_lounge.dto.request.review.ReviewUpdateDTO;
import com.project01_teamA.camping_lounge.dto.request.review.ReviewWriteDTO;
import com.project01_teamA.camping_lounge.entity.Review;
import com.project01_teamA.camping_lounge.exception.ResourceNotFoundException;
import com.project01_teamA.camping_lounge.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // List
    public Page<ReviewListDTO> getAll(Pageable pageable){
        Page<Review> page = reviewRepository.findAll(pageable);
        List<ReviewListDTO> list = page.stream().map(ReviewListDTO::fromEntity).toList();
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    // Write
    public ReviewWriteDTO write(ReviewWriteDTO reviewWriteDTO){
        Review review = reviewWriteDTO.toEntity();
        Review saveReview = reviewRepository.save(review);
        return ReviewWriteDTO.fromEntity(saveReview);
    }

    // detail
    public ReviewDetailDTO detail(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다. ID: " + reviewId));
        review.upViewCount();
        return ReviewDetailDTO.fromEntity(review);
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
        return ReviewDetailDTO.fromEntity(updatedReview);
    }

}