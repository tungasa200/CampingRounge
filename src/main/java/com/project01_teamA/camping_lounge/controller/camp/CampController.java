package com.project01_teamA.camping_lounge.controller.camp;

import com.project01_teamA.camping_lounge.dto.request.camp.CampUpdateDto;
import com.project01_teamA.camping_lounge.dto.request.camp.CampWriteDto;
import com.project01_teamA.camping_lounge.dto.response.camp.ResCampDetailDto;
import com.project01_teamA.camping_lounge.dto.response.camp.ResCampListDto;
import com.project01_teamA.camping_lounge.dto.response.camp.ResCampWriteDto;
import com.project01_teamA.camping_lounge.service.camp.CampService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //캠핑장 상세
    @GetMapping("/camp/{campId}")
    public ResponseEntity<ResCampDetailDto> detail(@PathVariable("campId") Long campId) {
        ResCampDetailDto findCampDto = campService.detail(campId);
        return ResponseEntity.status(HttpStatus.OK).body(findCampDto);
    }


    //어드민 페이지 요청
    //캠핑장 등록
    @PostMapping("/admin/camp/write")
    public ResponseEntity<ResCampWriteDto> write (@RequestBody CampWriteDto campDto) {
        Thread currentThread = Thread.currentThread();
        log.info("현재 실행중인 스레드" + currentThread.getName());
        ResCampWriteDto saveCampDto = campService.write(campDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveCampDto);
    }

    //캠핑장 상세
    @GetMapping("/admin/camp/{campId}")
    public ResponseEntity<ResCampDetailDto> adminCampDetail(@PathVariable("campId") Long campId) {
        ResCampDetailDto findCampDto = campService.detail(campId);
        return ResponseEntity.status(HttpStatus.OK).body(findCampDto);
    }

    // 캠핑장 수정
    @PatchMapping("/admin/camp/{campId}/update")
    public ResponseEntity<ResCampDetailDto> update(
            @PathVariable("campId") Long campId,
            @RequestBody CampUpdateDto campDTO) {
        ResCampDetailDto updateCampdDTO = campService.update(campId, campDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updateCampdDTO);
    }

    //체크한 캠핑장 삭제
    @DeleteMapping("/admin/camp/delete")
    public ResponseEntity<String> deleteCheck (@RequestBody List<Long> campId){
        campService.deleteChecked(campId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 캠핑장 상세에서 캠핑장 삭제
    @DeleteMapping("/admin/camp/{campId}/delete")
    public ResponseEntity<Long> delete (@PathVariable Long campId){
        campService.delete(campId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
