package com.project01_teamA.camping_lounge.service;

import com.project01_teamA.camping_lounge.dto.response.camp.ResCampListDto;
import com.project01_teamA.camping_lounge.entity.Campsite;
import com.project01_teamA.camping_lounge.repository.CampRepository;
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
}
