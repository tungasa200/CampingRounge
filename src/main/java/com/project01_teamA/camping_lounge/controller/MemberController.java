package com.project01_teamA.camping_lounge.controller;

import com.project01_teamA.camping_lounge.dto.MemberDto;
import com.project01_teamA.camping_lounge.dto.request.member.AdminRegisterDto;
import com.project01_teamA.camping_lounge.dto.request.member.MemberLoginDto;
import com.project01_teamA.camping_lounge.dto.request.member.MemberRegisterDto;
import com.project01_teamA.camping_lounge.dto.request.member.MemberUpdateDto;
import com.project01_teamA.camping_lounge.entity.Member;
import com.project01_teamA.camping_lounge.service.MemberService;
import com.project01_teamA.camping_lounge.service.ProfileFileService;
import com.project01_teamA.camping_lounge.service.SecurityKeyService;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
//@RequestMapping("/user")
@RequestMapping("/member")
//@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final SecurityKeyService securityKeyService;
    private final ProfileFileService profileFileService;

    public MemberController(MemberService memberService, SecurityKeyService securityKeyService, ProfileFileService profileFileService) {
        this.memberService = memberService;
        this.securityKeyService = securityKeyService;
        this.profileFileService = profileFileService;
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

    // 유저 기능

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberRegisterDto memberRegisterDto) {
        memberService.join(memberRegisterDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 성공");
    }

    @PostMapping("/adminJoin")
    public ResponseEntity<String> adminJoin(@RequestBody AdminRegisterDto adminRegisterDto) {
        memberService.adminJoin(adminRegisterDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 성공");
    }

    @PostMapping("/emailDuplicate")
    public ResponseEntity<Boolean> emailDuplicate(@RequestBody MemberRegisterDto memberRegisterDto) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.emailDuplicate(memberRegisterDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginDto memberLoginDto) {
        Member member = memberService.login(memberLoginDto);
        // true 면 로그인 성공, false 로그인 실패
        if (member.isEnable()) {
            //.header(member.getToken() : JWT 데이터들 header로 넘겨줘야 함(매우 중요)
            return ResponseEntity.status(HttpStatus.OK).header(member.getToken()).body(member);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 비활성화 상태입니다.");
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody MemberLoginDto memberLoginDto, HttpSession session) {
//        Member member = memberService.login(memberLoginDto);
//
//        // true 면 로그인 성공, false 로그인 실패
//        if (member.isEnable()) {
//            // 세션 유지 (jwt 대체 임시용)
//            //session.setAttribute("loggedInUser", member);
//            return ResponseEntity.status(HttpStatus.OK).body(member.getRole());
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 비활성화 상태입니다.");
//        }
//    }

    @GetMapping("/me")
    public ResponseEntity<?> getUser(HttpSession session) {
        Member member = (Member) session.getAttribute("loggedInUser");

        if (member == null) {
            System.out.println("세션에 사용자 정보 없음 (로그인이 필요합니다.)");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        MemberDto memberDto = MemberDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .tel(member.getTel())
                .role(member.getRole())
                .gender(member.getGender())
                .profile(member.isProfile())
                .join_date(member.getJoin_date())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(memberDto);
    }

    @GetMapping("/unable")
    public ResponseEntity<String> userUnable(HttpSession session) {
        Member member = (Member) session.getAttribute("loggedInUser");
        if (member.isEnable()) {
            memberService.userDisabled(member);
            session.removeAttribute("loggedInUser");
            return ResponseEntity.status(HttpStatus.OK).body("유저 비활성화 성공");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("이미 비활성화 상태입니다");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> userUpdate(HttpSession session,
                                             @RequestBody MemberUpdateDto memberUpdateDto) {

        Member member = (Member) session.getAttribute("loggedInUser");

        Member memberUpdate = memberService.userUpdate(member, memberUpdateDto);

        session.setAttribute("loggedInUser", memberUpdate);
        return ResponseEntity.status(HttpStatus.OK).body("회원 정보 수정 완료");
    }

    // 파일 기능

    @PostMapping("/upload")
    public ResponseEntity<String> fileUpload(HttpSession session, @RequestParam MultipartFile file) {
        Member member = (Member) session.getAttribute("loggedInUser");

        String response = profileFileService.uploadFile(member.getId(), file);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getProfile")
    public ResponseEntity<String> getProfileImagePath(HttpSession session) {
        Member member = (Member) session.getAttribute("loggedInUser");

        String response = profileFileService.getProfileImagePath(member.getId());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
