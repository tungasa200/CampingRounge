package com.project01_teamA.camping_lounge.controller;

import com.project01_teamA.camping_lounge.dto.request.member.MemberUpdateDto;
import com.project01_teamA.camping_lounge.entity.Member;
import com.project01_teamA.camping_lounge.repository.MemberRepository;
import com.project01_teamA.camping_lounge.service.MemberService;
import com.project01_teamA.camping_lounge.service.SecurityKeyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;
    private final SecurityKeyService securityKeyService;
    private final MemberRepository memberRepository;

    public AdminController(SecurityKeyService securityKeyService, MemberService memberService, MemberRepository memberRepository) {
        this.securityKeyService = securityKeyService;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return ResponseEntity.status(HttpStatus.OK).body(members);
    }

    @DeleteMapping("/delete/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.status(HttpStatus.OK).body("유저 삭제 성공");
    }

    @PutMapping("/update/{memberId}")
    public ResponseEntity<String> updateMember(@PathVariable Long memberId, @RequestBody MemberUpdateDto memberUpdateDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        memberService.userUpdate(member, memberUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body("유저 정보 수정 성공");
    }


    // 보안키 기능

    @GetMapping("/securityKey")
    public ResponseEntity<String> getSecurityKey() {
        return ResponseEntity.status(HttpStatus.OK).body(securityKeyService.getSecurityKey());
    }

    @GetMapping("/regenerateKey")
    public ResponseEntity<String> regenerateKey() {
        securityKeyService.generateKey();
        return ResponseEntity.status(HttpStatus.OK).body("Key Regenerated");
    }


}
