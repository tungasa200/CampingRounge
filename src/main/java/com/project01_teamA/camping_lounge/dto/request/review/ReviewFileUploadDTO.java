package com.project01_teamA.camping_lounge.dto.request.review;

import com.project01_teamA.camping_lounge.entity.ReviewFiles;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewFileUploadDTO {

    private Long fileId;
    private String originFileName;
    private String fileType;
    private String filePath;

    @Builder
    public ReviewFileUploadDTO(Long fileId, String originFileName, String fileType, String filePath) {
        this.fileId = fileId;
        this.originFileName = originFileName;
        this.fileType = fileType;
        this.filePath = filePath;
    }

    public static ReviewFileUploadDTO fromEntity(ReviewFiles files){
        return ReviewFileUploadDTO.builder()
                .fileId(files.getFileId())
                .originFileName(files.getOriginFileName())
                .fileType(files.getFileType())
                .filePath(files.getFilePath())
                .build();
    }

}
