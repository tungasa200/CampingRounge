package com.project01_teamA.camping_lounge.service;

import com.project01_teamA.camping_lounge.dto.request.member.AdminRegisterDto;
import com.project01_teamA.camping_lounge.dto.request.member.MemberLoginDto;
import com.project01_teamA.camping_lounge.dto.request.member.MemberUpdateDto;
import com.project01_teamA.camping_lounge.exception.MemberException;
import com.project01_teamA.camping_lounge.dto.request.member.MemberRegisterDto;
import com.project01_teamA.camping_lounge.entity.Member;
import com.project01_teamA.camping_lounge.repository.MemberRepository;
import com.project01_teamA.camping_lounge.security.jwt.CustomUserDetailsService;
import com.project01_teamA.camping_lounge.security.jwt.JwtTokenUtil;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityKeyService securityKeyService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

//    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, SecurityKeyService securityKeyService) {
//        this.memberRepository = memberRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.securityKeyService = securityKeyService;
//    }

    public void join(MemberRegisterDto memberRegisterDto) {

        // 비밀번호 암호화 하기
        String encodedPassword = passwordEncoder.encode(memberRegisterDto.getPassword());

        Member member = new Member();
        member.setEmail(memberRegisterDto.getEmail());
        member.setName(memberRegisterDto.getName());
        member.setPassword(encodedPassword);
        member.setGender(memberRegisterDto.getGender());
        member.setTel(memberRegisterDto.getTel());
        member.setJoin_date(memberRegisterDto.getJoin_date());
        member.setRole(memberRegisterDto.getRole());
        member.setEnable(true);


        // 데이터 베이스에 저장
        memberRepository.save(member);
    }

    public void adminJoin(AdminRegisterDto adminRegisterDto) {


        if (securityKeyService.validateSecurityKey(adminRegisterDto.getSecurityKey())) {
            // 비밀번호 암호화 하기
            String encodedPassword = passwordEncoder.encode(adminRegisterDto.getPassword());

            Member member = new Member();
            member.setEmail(adminRegisterDto.getEmail());
            member.setName(adminRegisterDto.getName());
            member.setPassword(encodedPassword);
            member.setGender(adminRegisterDto.getGender());
            member.setTel(adminRegisterDto.getTel());
            member.setJoin_date(adminRegisterDto.getJoin_date());
            member.setRole(adminRegisterDto.getRole());
            member.setEnable(true);


            // 데이터 베이스에 저장
            memberRepository.save(member);
        } else {
            throw new IllegalArgumentException("보안키가 맞지 않습니다.");
        }

    }

    public Member login(MemberLoginDto memberLoginDto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberLoginDto.getEmail());
        if(userDetails == null) {
            throw new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
        } else {
            String password = memberLoginDto.getPassword();
            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                authenticate(memberLoginDto.getEmail(), memberLoginDto.getPassword());
                String token = jwtTokenUtil.generateToken(userDetails);
                return Member.fromEntity(userDetails, token);
            } else {
                return null;
            }
        }

        //데이터 베이스에서 회원 정보 찾기
//        Member member = memberRepository.findByEmail(memberLoginDto.getEmail())
//                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
//
//        String password = memberLoginDto.getPassword();
//
//        //비밀번호 암호화 된거랑 매치되는지 확인
//        if (passwordEncoder.matches(password, member.getPassword())) {
//            authenticate(memberLoginDto.getEmail(), memberLoginDto.getPassword());
//            String token = jwtTokenUtil.generateToken(member);
//            if(token != null && !token.isEmpty()){
//                member.setToken(token);
//            }
//            return member;
//        } else {
//            return null;
//        }

    }

    public boolean emailDuplicate(MemberRegisterDto memberRegisterDto) {
        return memberRepository.findByEmail(memberRegisterDto.getEmail()).isPresent();
    }

    public void userDisabled(Member member) {
        member.setEnable(false);
        memberRepository.save(member);
    }

    public Member userUpdate(Member member, MemberUpdateDto memberUpdateDto) {

        String encodedPassword = passwordEncoder.encode(memberUpdateDto.getPassword());

        Member memberUpdate = memberRepository.findByEmail(member.getEmail()).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        memberUpdate.setPassword(encodedPassword);
        memberUpdate.setName(memberUpdateDto.getName());
        memberUpdate.setGender(memberUpdateDto.getGender());
        memberUpdate.setTel(memberUpdateDto.getTel());
        memberUpdate.setEmail(memberUpdateDto.getEmail());
        memberUpdate.setProfile(memberUpdateDto.isProfile());

        memberRepository.save(memberUpdate);

        return memberUpdate;
    }


    /**
     * 사용자 인증
     * @param email
     * @param pwd
     */
    private void authenticate(String email, String pwd) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, pwd));
        } catch (DisabledException e) {
            throw new MemberException("인증되지 않은 아이디입니다.", HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException e) {
            throw new MemberException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}