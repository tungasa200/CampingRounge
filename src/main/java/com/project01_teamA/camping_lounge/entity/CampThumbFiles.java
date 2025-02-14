package com.project01_teamA.camping_lounge.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CampThumbFiles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="THUMB_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAMP_ID")
    private Campsite campsite;

    @Column
    private String originFileName;

    @Column(name = "FILE_TYPE")
    private String fileType;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Builder
    public CampThumbFiles(Long id, String originFileName, String fileType, String filePath) {
        this.id = id;
        this.originFileName = originFileName;
        this.fileType = fileType;
        this.filePath = filePath;
    }
    public void setMappingCamp(Campsite campsite) {this.campsite = campsite;}
}
