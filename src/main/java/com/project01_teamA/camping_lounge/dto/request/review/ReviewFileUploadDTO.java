package com.project01_teamA.camping_lounge.dto.request.review;

import com.project01_teamA.camping_lounge.entity.ReviewFiles;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewFileUploadDTO {

    private Long fileId;
    private String originFileName;
    private String storedFileName;

    @Builder
    public ReviewFileUploadDTO(Long fileId, String originFileName, String storedFileName) {
        this.fileId = fileId;
        this.originFileName = originFileName;
        this.storedFileName = storedFileName;
    }

    public static ReviewFileUploadDTO fromEntity(ReviewFiles files){
        return ReviewFileUploadDTO.builder()
                .fileId(files.getFileId())
                .originFileName(files.getOriginFileName())
                .storedFileName(files.getStoredFileName())
                .build();
    }

}
