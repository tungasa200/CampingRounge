package com.project01_teamA.camping_lounge.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ReviewFiles {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(name = "origin_file_name", nullable = false)
    private String originFileName;

    @Column(name = "stored_file_name", nullable = false)
    private String storedFileName;

    @Builder
    public ReviewFiles(Long fileId, Review review, String originFileName, String storedFileName) {
        this.fileId = fileId;
        this.review = review;
        this.originFileName = originFileName;
        this.storedFileName = storedFileName;
    }
}
