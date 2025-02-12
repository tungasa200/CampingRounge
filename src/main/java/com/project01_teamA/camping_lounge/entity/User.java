package com.project01_teamA.camping_lounge.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
//해당 클래스 제거해야함 (참고& 구현하고 제거)
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Builder
    public User(String email, String password, String auth) {
        this.email = email;
        this.password = password;
    }

    //권한 목록 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
    
    //계정 만료 여부 확인
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠금 여부 확인
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호 만료 여부 확인
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정 사용 가능 여부 확인
    @Override
    public boolean isEnabled() {
        return true;
    }
}