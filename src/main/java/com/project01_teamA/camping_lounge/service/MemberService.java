package com.project01_teamA.camping_lounge.service;

import com.project01_teamA.camping_lounge.dto.request.member.AdminRegisterDto;
import com.project01_teamA.camping_lounge.dto.request.member.MemberLoginDto;
import com.project01_teamA.camping_lounge.dto.request.member.MemberUpdateDto;
import com.project01_teamA.camping_lounge.dto.response.member.MemberResponseDto;
import com.project01_teamA.camping_lounge.dto.response.member.MemberTokenDto;
import com.project01_teamA.camping_lounge.exception.MemberException;
import com.project01_teamA.camping_lounge.dto.request.member.MemberRegisterDto;
import com.project01_teamA.camping_lounge.entity.Member;
import com.project01_teamA.camping_lounge.exception.ResourceNotFoundException;
import com.project01_teamA.camping_lounge.repository.MemberRepository;
import com.project01_teamA.camping_lounge.repository.ProfileFilesRepository;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityKeyService securityKeyService;
    private final ProfileFilesRepository profileFilesRepository;
    private final ProfileFileService profileFileService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    public Member join(MemberRegisterDto memberRegisterDto) {

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
        member.setAddress(memberRegisterDto.getAddress());
        member.setAddress_detail(memberRegisterDto.getAddress_detail());
        member.setPostcode(memberRegisterDto.getPostcode());


        // 데이터 베이스에 저장
        Member updatedMember = memberRepository.save(member);

        return updatedMember;
    }


    public MemberTokenDto login(MemberLoginDto memberLoginDto) {
        //UserDetailsService : Spring Security에서 사용자의 정보를 가져올 때 사용하는 서비스
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberLoginDto.getEmail());

        authenticate(memberLoginDto.getEmail(), memberLoginDto.getPassword());

        String password = memberLoginDto.getPassword();
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            String token = jwtTokenUtil.generateToken(userDetails);
            return MemberTokenDto.fromEntity(userDetails, token);
        } else {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

    }

    public boolean emailDuplicate(MemberRegisterDto memberRegisterDto) {
        return memberRepository.findByEmail(memberRegisterDto.getEmail()).isPresent();
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Transactional
    public void deleteMember(Long id) {
        try {
            if (profileFilesRepository.findByMemberId(id).isPresent()) {
                profileFileService.deleteFile(id);
                profileFilesRepository.deleteByMemberId(id);
            }
            memberRepository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    public void userDisabled(Member member) {
        member.setEnable(false);
        memberRepository.save(member);
    }

    //
    public MemberResponseDto userUpdate(Member member, MemberUpdateDto updateDto) {
        String encodedPassword = passwordEncoder.encode(updateDto.getPassword());
        Member updateMember =  memberRepository.findByEmail(member.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member Email", member.getEmail())
        );
        updateMember.update(encodedPassword, updateDto);
        return MemberResponseDto.fromEntity(updateMember);
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

    public MemberResponseDto getUser(Member member) {
        Member getUser = memberRepository.findByEmail(member.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member Email", member.getEmail())
        );
        return MemberResponseDto.fromEntity(getUser);
    }
}