package com.project01_teamA.camping_lounge.dto.request.member;



import com.project01_teamA.camping_lounge.entity.Member;
import com.project01_teamA.camping_lounge.common.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -Request-
 * 회원 가입 요청 dto
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberRegisterDto2 {

    private String email;
    private String password;
    private String passwordCheck;
    private String username;

    @Builder
    public MemberRegisterDto2(String email, String password, String passwordCheck, String username) {
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.username = username;
    }

    // DTO -> Entity
    public static Member ofEntity(MemberRegisterDto2 dto) {
        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getUsername())
                .role(Role.USER)
                .build();
    }
}
