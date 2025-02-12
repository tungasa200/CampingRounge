package com.project01_teamA.camping_lounge.controller;

import com.project01_teamA.camping_lounge.dto.MemberDto;
import com.project01_teamA.camping_lounge.entity.Member;
import com.project01_teamA.camping_lounge.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = adminService.getAllMembers();
        return ResponseEntity.status(HttpStatus.OK).body(members);
    }

    @DeleteMapping("/delete/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable Long memberId) {
        adminService.deleteMember(memberId);
        return ResponseEntity.status(HttpStatus.OK).body("유저 삭제 성공");
    }

    @PutMapping("/update/{memberId}")
    public ResponseEntity<String> updateMember(@PathVariable Long memberId, @RequestBody MemberDto memberDto) {
        adminService.updateMember(memberId, memberDto);
        return ResponseEntity.status(HttpStatus.OK).body("유저 정보 수정 성공");
    }
}
