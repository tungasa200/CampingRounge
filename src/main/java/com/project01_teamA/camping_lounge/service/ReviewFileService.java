package com.project01_teamA.camping_lounge.service;

import com.project01_teamA.camping_lounge.dto.request.review.ReviewFileUploadDTO;
import com.project01_teamA.camping_lounge.entity.Review;
import com.project01_teamA.camping_lounge.entity.ReviewFiles;
import com.project01_teamA.camping_lounge.repository.ReviewFileRepository;
import com.project01_teamA.camping_lounge.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewFileService {

    private final ReviewRepository reviewRepository;
    private final ReviewFileRepository reviewFileRepository;

    @Value("${project.folderPath}")
    private String FOLDER_PATH;

    public List<ReviewFileUploadDTO> uploadFile(Long reviewId, List<MultipartFile> files) throws IOException {
        // 리뷰 찾기
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new RuntimeException("Review not found")
        );

        // 파일 리스트 처리
        List<ReviewFileUploadDTO> fileUploadDTOList = new ArrayList<>();

        for (MultipartFile file : files) {
            // 파일 이름 처리
            String originFileName = file.getOriginalFilename();
            if (originFileName == null || !originFileName.contains(".")) {
                throw new IllegalArgumentException("Invalid file type");
            }
            String randomId = UUID.randomUUID().toString();
            String storedFileName = randomId + originFileName.substring(originFileName.lastIndexOf("."));

            // 저장 경로 설정
            String filePath = FOLDER_PATH + File.separator + storedFileName;

            // 폴더가 없으면 생성
            File f = new File(FOLDER_PATH);
            if (!f.exists()) {
                f.mkdirs();
            }

            // 파일 저장
            Files.copy(file.getInputStream(), Paths.get(filePath));

            // ReviewFiles 엔티티에 데이터 저장
            ReviewFiles reviewFile = new ReviewFiles();
            reviewFile.setReview(review);
            reviewFile.setOriginFileName(originFileName);
            reviewFile.setStoredFileName(storedFileName);

            ReviewFiles savedFile = reviewFileRepository.save(reviewFile);

            // DTO로 변환해서 리스트에 추가
            fileUploadDTOList.add(ReviewFileUploadDTO.fromEntity(savedFile));
        }

        return fileUploadDTOList;
    }

}
