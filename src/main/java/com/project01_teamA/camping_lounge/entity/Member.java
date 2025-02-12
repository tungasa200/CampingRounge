package com.project01_teamA.camping_lounge.entity;

import com.project01_teamA.camping_lounge.common.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Member extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, name = "member_email", unique = true)
    private String email;

    @Column(nullable = false, name = "member_name")
    private String name;

    @Column(nullable = false, name = "member_password")
    private String password;

    // 문자열로 sql에 저장되게 하기
    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private Role role;

    @Column(name = "member_gender")
    private String gender;

    @Column(nullable = false, name = "member_tel")
    private String tel;

    @Column(name = "member_enable")
    private boolean enable;

    @Column(name = "member_profile")
    private boolean profile;

    @Column(name = "member_disabled_date")
    private Date disabled_date;

    @Column(name = "member_delete_date")
    private Date delete_date;

    @Column(name = "member_join_date")
    private Date join_date;

    private String token;

    //@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //public List<Board> boards = new ArrayList<>();

    //@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //public List<Comment> comments = new ArrayList<>();

    //== 생성자 Builder ==//
    @Builder
    public Member(String gender, Long id, String email, String name, String password, Role role, String tel, boolean enable, boolean profile, Date disabled_date, Date delete_date, Date join_date, String token) {
        this.gender = gender;
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.tel = tel;
        this.enable = enable;
        this.profile = profile;
        this.disabled_date = disabled_date;
        this.delete_date = delete_date;
        this.join_date = join_date;
        this.token = token;
    }

    // Entity -> DTO
    public static Member fromEntity(UserDetails userDetails, String token) {
        if (!(userDetails instanceof Member)) {
            throw new IllegalArgumentException("UserDetails 객체가 Member 타입이 아닙니다.");
        }

        Member member = (Member) userDetails; // ✅ UserDetails → Member 변환

        return Member.builder()
                .id(member.getId())              // ✅ ID 추가
                .email(member.getUsername())     // ✅ 이메일 추가
                .name(member.getName())          // ✅ 이름 추가
                .gender(member.getGender())      // ✅ 성별 추가
                .password(member.getPassword())  // ✅ 비밀번호 추가
                .role(member.getRole())          // ✅ 역할 추가
                .tel(member.getTel())            // ✅ 전화번호 추가
                .enable(member.isEnable())       // ✅ 활성화 상태 추가
                .profile(member.isProfile())     // ✅ 프로필 상태 추가
                .disabled_date(member.getDisabled_date())  // ✅ 비활성화 날짜 추가
                .delete_date(member.getDelete_date())      // ✅ 삭제 날짜 추가
                .join_date(member.getJoin_date())         // ✅ 가입 날짜 추가
                .token(token)                             // ✅ JWT 토큰 추가
                .build();
    }

    //== update ==//
    public void update(String password, String name) {
        this.password = password;
        this.name = name;
    }

    //========== UserDetails implements ==========//
    /**
     * Token을 고유한 Email 값으로 생성합니다
     * @return email;
     */
    @Override
    public String getUsername() {
        return email;
    }

    //Authentication 객체에 부여된 권한을 String으로 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add( new SimpleGrantedAuthority("ROLE_" + this.role.name()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}