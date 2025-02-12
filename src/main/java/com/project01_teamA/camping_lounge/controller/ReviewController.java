package com.project01_teamA.camping_lounge.controller;

import com.project01_teamA.camping_lounge.dto.response.review.ReviewDetailDTO;
import com.project01_teamA.camping_lounge.dto.response.review.ReviewListDTO;
import com.project01_teamA.camping_lounge.dto.request.review.ReviewUpdateDTO;
import com.project01_teamA.camping_lounge.dto.request.review.ReviewWriteDTO;
import com.project01_teamA.camping_lounge.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 목록
    @GetMapping("/list")
    public ResponseEntity<Page<ReviewListDTO>> boardList(
            @PageableDefault(size = 10, sort = "reviewId", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ReviewListDTO> listDTO = reviewService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listDTO);
    }

    // 리뷰 작성
    @PostMapping("/post")
    public ResponseEntity<?> postReview(@RequestBody ReviewWriteDTO reviewWriteDTO) {
        if (reviewWriteDTO.getReviewTitle() == null || reviewWriteDTO.getReviewTitle().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("제목을 입력하세요.");
        }
        ReviewWriteDTO savedReview = reviewService.write(reviewWriteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    // 리뷰 상세
    @GetMapping("/detail/{reviewId}")
    public ResponseEntity<ReviewDetailDTO> getReviewDetail(@PathVariable Long reviewId) {
        ReviewDetailDTO reviewDetail = reviewService.detail(reviewId);
        return ResponseEntity.ok(reviewDetail);
    }

    // 리뷰 삭제
    @PostMapping("/delete")
    public ResponseEntity<Long> delete(Long reviewId){
        reviewService.delete(reviewId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    // 리뷰 수정
    @PostMapping("/update/{reviewId}")
    public ResponseEntity<ReviewDetailDTO> updateReview(
            @PathVariable Long reviewId, @RequestBody ReviewUpdateDTO reviewUpdateDTO) {
        try {
            ReviewDetailDTO updatedReview = reviewService.update(reviewId, reviewUpdateDTO);
            return ResponseEntity.ok(updatedReview);  // 수정된 리뷰 상세 반환
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ReviewDetailDTO());
        }

    }
}
