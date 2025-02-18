package com.project01_teamA.camping_lounge.service;

import com.project01_teamA.camping_lounge.dto.response.review.CommentDTO;
import com.project01_teamA.camping_lounge.dto.request.review.CommentWriteDTO;
import com.project01_teamA.camping_lounge.entity.Comment;
import com.project01_teamA.camping_lounge.entity.Member;
import com.project01_teamA.camping_lounge.entity.Review;
import com.project01_teamA.camping_lounge.exception.ResourceNotFoundException;
import com.project01_teamA.camping_lounge.repository.CommentRepository;
import com.project01_teamA.camping_lounge.repository.MemberRepository;
import com.project01_teamA.camping_lounge.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    // comment list
    public Page<CommentDTO> getAllComments(Pageable pageable, Long reviewId) {
        Page<Comment> commentPage = commentRepository.findAllWithMemberAndReview(pageable, reviewId);
        return commentPage.map(CommentDTO::fromEntity);
    }

    // comment write
    public CommentWriteDTO writeComment(CommentWriteDTO commentWriteDTO) {

        Review review = reviewRepository.findById(commentWriteDTO.getReviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Review", "Review Id", String.valueOf(commentWriteDTO.getReviewId())));
        Member member = memberRepository.findById(commentWriteDTO.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", "Member Id", String.valueOf(commentWriteDTO.getMemberId())));

        Comment comment = commentWriteDTO.toEntity(review, member);
        commentRepository.save(comment);
        return commentWriteDTO;
    }

    // comment delete
    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}