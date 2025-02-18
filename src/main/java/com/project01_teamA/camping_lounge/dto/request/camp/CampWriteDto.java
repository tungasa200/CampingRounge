package com.project01_teamA.camping_lounge.dto.request.camp;

import com.project01_teamA.camping_lounge.entity.Campsite;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CampWriteDto {
    private String campName;
    private String campInfo;
    private String campTel;
    private String campAddressDo;
    private String campAddressGungu;
    private String campAddress1;
    private String campAddress2;
    private String campMapX;
    private String campMapY;
    private Boolean toilet;
    private Boolean hotWater;
    private Boolean electric;
    private Boolean fireWood;
    private Boolean wifi;
    private Boolean playGround;
    private Boolean pet;
    private Boolean swimming;
    private Integer totalCapacity;

    private MultipartFile thumb;
    private List<MultipartFile> images;

    @Builder
    public CampWriteDto(String campName, String campInfo, String campTel, String campAddressDo, String campAddressGungu, String campAddress1, String campAddress2, String campMapX, String campMapY, Boolean toilet, Boolean hotWater, Boolean electric, Boolean fireWood, Boolean wifi, Boolean playGround, Boolean pet, Boolean swimming, Integer totalCapacity, MultipartFile thumb, List<MultipartFile> images) {
        this.campName = campName;
        this.campInfo = campInfo;
        this.campTel = campTel;
        this.campAddressDo = campAddressDo;
        this.campAddressGungu = campAddressGungu;
        this.campAddress1 = campAddress1;
        this.campAddress2 = campAddress2;
        this.campMapX = campMapX;
        this.campMapY = campMapY;
        this.toilet = toilet;
        this.hotWater = hotWater;
        this.electric = electric;
        this.fireWood = fireWood;
        this.wifi = wifi;
        this.playGround = playGround;
        this.pet = pet;
        this.swimming = swimming;
        this.totalCapacity = totalCapacity;
        this.thumb = thumb;
        this.images = images;
    }

    public static Campsite ofEntity(CampWriteDto dto) {
        return Campsite.builder()
                .campName(dto.getCampName())
                .campInfo(dto.getCampInfo())
                .campTel(dto.getCampTel())
                .campAddressDo(dto.getCampAddressDo())
                .campAddressGungu(dto.getCampAddressGungu())
                .campAddress1(dto.getCampAddress1())
                .campAddress2(dto.getCampAddress2())
                .campMapX(dto.getCampMapX())
                .campMapY(dto.getCampMapY())
                .toilet(dto.getToilet())
                .hotWater(dto.getHotWater())
                .electric(dto.getElectric())
                .fireWood(dto.getFireWood())
                .wifi(dto.getWifi())
                .playGround(dto.getPlayGround())
                .pet(dto.getPet())
                .swimming(dto.getSwimming())
                .totalCapacity(dto.getTotalCapacity())
                .build();
    }
}
