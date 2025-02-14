package com.project01_teamA.camping_lounge.controller;

import com.project01_teamA.camping_lounge.dto.response.camp.ResCampListDto;
import com.project01_teamA.camping_lounge.service.CampService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CampController {
    private final CampService campService;
    
    //전체보기
    @GetMapping("/camp/list")
    public ResponseEntity<Page<ResCampListDto>> campList(
            @PageableDefault(size = 9, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        Page<ResCampListDto> listDto = campService.getAllCamps(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }
    
    //서치, 필터
    @GetMapping("/camp/search")
    public ResponseEntity<Page<ResCampListDto>> searchCamp(
            @RequestParam(required = false) String search,
            @RequestParam(required = false)List<String> filters,
            @PageableDefault(size = 9, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
            Page<ResCampListDto> listDto = campService.getFilteredCamps(search, filters, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

}
