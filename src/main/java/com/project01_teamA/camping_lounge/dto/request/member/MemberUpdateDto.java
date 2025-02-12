package com.project01_teamA.camping_lounge.dto.request.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -Request-
 * 회원 정보 변경 요청 dto
 */

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateDto {

    private String email;
    private String name;
    private String password;
    private String gender;
    private String tel;
    private boolean profile;

    @Builder
    public MemberUpdateDto(String email, String name, String password, String gender, String tel, boolean profile) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.tel = tel;
        this.profile = profile;
    }
}

