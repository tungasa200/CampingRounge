package com.project01_teamA.camping_lounge.dto.response.camp;

import com.project01_teamA.camping_lounge.entity.CampThumbFiles;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResCampThumbFilesDto {
    private Long id;
    private String originFileName;
    private String fileType;

    @Builder
    public ResCampThumbFilesDto(Long id, String originFileName, String fileType) {
        this.id = id;
        this.originFileName = originFileName;
        this.fileType = fileType;
    }

    public static ResCampThumbFilesDto fromEntity(CampThumbFiles thumb){
        return ResCampThumbFilesDto.builder()
                .id(thumb.getId())
                .originFileName(thumb.getOriginFileName())
                .fileType(thumb.getFileType())
                .build();
    }
}
