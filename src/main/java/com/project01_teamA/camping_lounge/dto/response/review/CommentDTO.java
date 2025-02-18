package com.project01_teamA.camping_lounge.dto.response.review;

import com.project01_teamA.camping_lounge.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDTO {

    private Long commentId;
    private Long memberId;
    private String commentContent;

    @Builder

    public CommentDTO(Long commentId, Long memberId, String commentContent) {
        this.commentId = commentId;
        this.memberId = memberId;
        this.commentContent = commentContent;
    }

    public static CommentDTO fromEntity(Comment comment){
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .memberId(comment.getMember().getId())
                .commentContent(comment.getCommentContent())
                .build();
    }
}
