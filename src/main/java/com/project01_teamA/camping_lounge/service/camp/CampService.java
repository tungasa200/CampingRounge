package com.project01_teamA.camping_lounge.service.camp;

import com.project01_teamA.camping_lounge.dto.request.camp.CampUpdateDto;
import com.project01_teamA.camping_lounge.dto.request.camp.CampWriteDto;
import com.project01_teamA.camping_lounge.dto.response.camp.ResCampDetailDto;
import com.project01_teamA.camping_lounge.dto.response.camp.ResCampListDto;
import com.project01_teamA.camping_lounge.dto.response.camp.ResCampWriteDto;
import com.project01_teamA.camping_lounge.entity.Campsite;
import com.project01_teamA.camping_lounge.exception.ResourceNotFoundException;
import com.project01_teamA.camping_lounge.repository.camp.CampRepository;
import com.project01_teamA.camping_lounge.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CampService {
    private final CampRepository campRepository;
    private final MemberRepository memberRepository;

    //전체 조회
    public Page<ResCampListDto> getAllCamps(Pageable pageable) {
        Page<Campsite> camps = campRepository.findAll(pageable);
        List<ResCampListDto> list = camps.getContent().stream()
                .map(ResCampListDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, camps.getTotalElements());
    }

    //검색 필터 기능
    public Page<ResCampListDto> getFilteredCamps(String search, List<String> filters, Pageable pageable) {
        return campRepository.findFilteredCamps(search, filters, pageable);
    }

    //캠핑장 상세
    public ResCampDetailDto detail(Long campId) {
        Campsite findCamp = campRepository.findById(campId).orElseThrow(
                () -> new ResourceNotFoundException("Camp", "Camp Id", String.valueOf(campId))
        );
        findCamp.upViewCount();
        return ResCampDetailDto.fromEntity(findCamp);
    }

    //캠핑장 등록
    public ResCampWriteDto write(CampWriteDto campDto) {
        Campsite campsite = CampWriteDto.ofEntity(campDto);
        Campsite savedCamp = campRepository.save(campsite);
        return ResCampWriteDto.fromEntity(savedCamp);
    }
    
    //캠핑장 수정
    public ResCampDetailDto update(Long campId, CampUpdateDto campDTO) {
        Campsite updateCamp = campRepository.findById(campId).orElseThrow(
                () -> new ResourceNotFoundException("Camp", "Camp Id", String.valueOf(campId))
        );
        updateCamp.update(campDTO.getCampName(), campDTO.getCampInfo(), campDTO.getCampTel(), campDTO.getCampAddressDo(), campDTO.getCampAddressGungu(), campDTO.getCampAddress1(), campDTO.getCampAddress2(), campDTO.getCampMapX(), campDTO.getCampMapY(),campDTO.getToilet(),campDTO.getHotWater(),campDTO.getElectric(),campDTO.getFireWood(),campDTO.getWifi(),campDTO.getPlayGround(),campDTO.getPet(),campDTO.getSwimming(),campDTO.getTotalCapacity());
        return ResCampDetailDto.fromEntity(updateCamp);
    }

    //체크한 캠핑장 삭제
    public void deleteChecked(List<Long> campId) {
        campRepository.deleteAllByIdInBatch(campId);
    }
    //캠핑장 상세에서 삭제
    public void delete(Long campId) {
        campRepository.deleteById(campId);
    }
}
