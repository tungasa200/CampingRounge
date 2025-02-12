package com.project01_teamA.camping_lounge.controller;

import com.project01_teamA.camping_lounge.dto.request.review.ReviewFileUploadDTO;
import com.project01_teamA.camping_lounge.service.ReviewFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewFileController {

    private final ReviewFileService reviewFileService;

    @PostMapping("/{reviewId}/upload")
    public ResponseEntity<List<ReviewFileUploadDTO>> upload (
            @PathVariable Long reviewId,
            @RequestParam("files") List<MultipartFile> files) throws IOException {
        List<ReviewFileUploadDTO> saveFile = reviewFileService.uploadFile(reviewId, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveFile);
    }



}
